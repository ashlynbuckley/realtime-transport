package com.transport.flink.job;

import com.fyp.avro.AvroVehicleEvent;
import com.transport.flink.source.KafkaSourceFactory;
import com.transport.flink.sink.MySQLSinkFactory;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkJob {
    public static void main(String[] args) throws Exception {
        //Environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        System.out.println("Flink Job Started");

        //Source
        // TODO: Duplicate records needs to be handled
        DataStream<AvroVehicleEvent> kafkaStream = KafkaSourceFactory.createKafkaSource(env);
        if (kafkaStream != null) {
            System.out.println("Stream is not null");
            parseRouteIds(kafkaStream);
        }
//        parseRouteIds(kafkaStream);
        //Sink
//        kafkaStream.addSink(MySQLSinkFactory.configureSink());

        env.execute("Process Messages");

    }

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