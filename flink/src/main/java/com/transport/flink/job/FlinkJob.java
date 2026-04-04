package com.transport.flink.job;

import com.fyp.core.avro.AvroVehicleEvent;
import com.fyp.core.avro.AvroTripUpdateEvent;
import com.fyp.core.avro.AvroStopTimeUpdate;
import com.fyp.core.avro.AvroRouteMetric;
import com.transport.flink.process.DelayAggregate;
import com.transport.flink.process.DelayProcessWindowFunction;
import com.transport.flink.process.MetricMapper;
import com.transport.flink.process.observation.DelayObservation;
import com.transport.flink.process.observation.DelayType;
import com.transport.flink.sink.KafkaSinkFactory;
import com.transport.flink.source.KafkaSourceFactory;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.time.Duration;

public class FlinkJob {
    public static void main(String[] args) throws Exception {
        //Environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //Topics
        final String TRIP_TOPIC = "trip-update-topic";
        final String VEHICLE_TOPIC = "vehicle-topic";
        final String ROUTE_METRIC_TOPIC = "route-metric-topic";

        System.out.println("Flink Job Started");

        //Source
        DataStream<AvroVehicleEvent> kafkaVehicleStream = KafkaSourceFactory.createKafkaSource(env, VEHICLE_TOPIC, "flink-vehicle-group", AvroVehicleEvent.class);
        DataStream<AvroTripUpdateEvent> kafkaTripUpdateStream = KafkaSourceFactory.createKafkaSource(env, TRIP_TOPIC, "flink-trip-update-group", AvroTripUpdateEvent.class);

        //Sink
        KafkaSink<AvroRouteMetric> kafkaSink = KafkaSinkFactory.configureKafkaSink(env, ROUTE_METRIC_TOPIC);

        if (kafkaVehicleStream != null) {
            System.out.println("Vehicle stream created");
        }
        else {
            System.out.println("Vehicle stream is null");
        }
        if (kafkaTripUpdateStream != null) {
            System.out.println("Trip update stream created");
        }
        else {
            System.out.println("Trip update stream is null");
        }

        assert kafkaTripUpdateStream != null;

        //Flatten stream
        DataStream<com.transport.flink.process.observation.DelayObservation> delayStream = flattenStopTimeUpdates(kafkaTripUpdateStream);

        //needed for event time windows (retrieve timestamps from the data itself)
        delayStream = delayStream
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy
                                //set the max delay for events that may arrive late (as in out of order)
                                .<DelayObservation>forBoundedOutOfOrderness(Duration.ofSeconds(30))
                                .withTimestampAssigner((event, timestamp) -> event.getTimestamp())
                );

        //Aggregate metrics
        //Firstly, key by route ID
        KeyedStream<com.transport.flink.process.observation.DelayObservation, String> keyedByRouteId =
                delayStream.keyBy(com.transport.flink.process.observation.DelayObservation::getRouteId);

        //fine granularity - 5mins
        keyedByRouteId
                //set the time windows
                .window(TumblingEventTimeWindows.of(Time.minutes(5)))
                //accumulate and produce the metrics after every window time closes
                .aggregate(new DelayAggregate(),
                        new DelayProcessWindowFunction())
                //map RouteMetric to AvroRouteMetric
                .map(new MetricMapper())
                .sinkTo(kafkaSink);

        //medium granularity - 30mins
        keyedByRouteId
                .window(TumblingEventTimeWindows.of(Time.minutes(30)))
                .aggregate(new DelayAggregate(),
                        new DelayProcessWindowFunction())
                .map(new MetricMapper())
                .sinkTo(kafkaSink);

        //coarse granularity - 1hr
        keyedByRouteId
                .window(TumblingEventTimeWindows.of(Time.hours(1)))
                .aggregate(new DelayAggregate(),
                        new DelayProcessWindowFunction())
                .map(new MetricMapper())
                .sinkTo(kafkaSink);

        env.execute("Transport-metrics");
    }

    private static DataStream<com.transport.flink.process.observation.DelayObservation> flattenStopTimeUpdates(DataStream<AvroTripUpdateEvent> inputStream) {
        return inputStream.flatMap(
                new FlatMapFunction<AvroTripUpdateEvent, com.transport.flink.process.observation.DelayObservation>() {

                    @Override
                    public void flatMap(AvroTripUpdateEvent event, Collector<com.transport.flink.process.observation.DelayObservation> out) {
                        //Skip over any that are empty
                        if (event.getStopTimeUpdates() == null) {
                            return;
                        }
                        //Start splitting updates w/ several delays into separate observations
                        for (AvroStopTimeUpdate update : event.getStopTimeUpdates()) {
                            //Arrival delay
                            if (update.getADelay() != null) {
                                DelayType delayType = DelayType.ARRIVAL;
                                //Collect the delay
                                out.collect(new com.transport.flink.process.observation.DelayObservation(
                                        event.getRouteId().toString(),
                                        event.getTripId().toString(),
                                        delayType,
                                        update.getADelay(),
                                        event.getTripUpdateTimestamp().toString()
                                ));
                            }
                            //Departure delay
                            if (update.getDDelay() != null) {
                                DelayType delayType = DelayType.DEPARTURE;
                                //Collect the delay
                                out.collect(new com.transport.flink.process.observation.DelayObservation(
                                        event.getRouteId().toString(),
                                        event.getTripId().toString(),
                                        delayType,
                                        update.getDDelay(),
                                        event.getTripUpdateTimestamp().toString()
                                ));
                            }
                        }
                    }
                }
        );
    }
}