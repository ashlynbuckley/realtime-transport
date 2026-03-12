package com.transport.flink.job;

import com.fyp.avro.AvroTripUpdateEvent;
import com.fyp.avro.AvroVehicleEvent;
import com.transport.flink.sink.KafkaSinkFactory;
import com.transport.flink.source.KafkaSourceFactory;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkJob {
    public static void main(String[] args) throws Exception {
        //Environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //Topics
        final String TRIP_TOPIC = "trip-update-topic";
        final String VEHICLE_TOPIC = "vehicle-topic";

        System.out.println("Flink Job Started");

        //Source
        // TODO: Duplicate records needs to be handled
        DataStream<AvroVehicleEvent> kafkaVehicleStream = KafkaSourceFactory.createKafkaSource(env, VEHICLE_TOPIC, "flink-vehicle-group", AvroVehicleEvent.class);
        DataStream<AvroTripUpdateEvent> kafkaTripUpdateStream = KafkaSourceFactory.createKafkaSource(env, TRIP_TOPIC, "flink-trip-update-group", AvroTripUpdateEvent.class);

        if (kafkaVehicleStream != null) {
            System.out.println("Vehicle stream created");
            //Sink
        }
        else {
            System.out.println("Vehicle stream is null");
        }
        if (kafkaTripUpdateStream != null) {
            System.out.println("Trip update stream created");
            //Sink
        }
        else {
            System.out.println("Trip update stream is null");
        }

        env.execute("Process Messages");
    }
    //for testing
    private static void parseRouteIds(DataStream<AvroVehicleEvent> kafkaStream) {
        System.out.println("Parsing Route Ids");
        //Note: event isn't giving back String, it gives CharSequence
        DataStream<AvroVehicleEvent> parsedRoute = kafkaStream.filter(event -> {
            CharSequence cs = event.getRouteId();
            return cs != null && cs.toString().equals("5401_126001");
        });
        parsedRoute.print();
    }
}