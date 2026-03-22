package com.transport.flink.job;

import com.fyp.avro.AvroStopTimeUpdate;
import com.fyp.avro.AvroTripUpdateEvent;
import com.fyp.avro.AvroVehicleEvent;
import com.transport.flink.process.DelayAggregate;
import com.transport.flink.process.DelayProcessWindowFunction;
import com.transport.flink.process.observation.DelayType;
import com.transport.flink.sink.KafkaSinkFactory;
import com.transport.flink.source.KafkaSourceFactory;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class FlinkJob {
    public static void main(String[] args) throws Exception {
        //Environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //Topics
        final String TRIP_TOPIC = "trip-update-topic";
        final String VEHICLE_TOPIC = "vehicle-topic";

        System.out.println("Flink Job Started");

        //Source
        DataStream<AvroVehicleEvent> kafkaVehicleStream = KafkaSourceFactory.createKafkaSource(env, VEHICLE_TOPIC, "flink-vehicle-group", AvroVehicleEvent.class);
        DataStream<AvroTripUpdateEvent> kafkaTripUpdateStream = KafkaSourceFactory.createKafkaSource(env, TRIP_TOPIC, "flink-trip-update-group", AvroTripUpdateEvent.class);

        //Sink
        KafkaSinkFactory kafkaSinkFactory = new KafkaSinkFactory();
        KafkaSink kafkaSink = kafkaSinkFactory.configureKafkaSink();

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
        //TODO: Add a counter for cancellations
        //Aggregate metrics
        KeyedStream<com.transport.flink.process.observation.DelayObservation, String> keyedByRouteId =
                delayStream.keyBy(com.transport.flink.process.observation.DelayObservation::getRouteId);

        //fine granularity - 5mins
        keyedByRouteId
                .window(TumblingEventTimeWindows.of(Time.minutes(5)))
                .aggregate(new DelayAggregate(),
                        new DelayProcessWindowFunction())
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
                                        update.getADelay()
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
                                        update.getDDelay()
                                ));
                            }
                        }
                    }
                }
        );
    }
}