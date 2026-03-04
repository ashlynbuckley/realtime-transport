package com.transport.flink.job;

import com.transport.flink.process.Bus;
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
//        DataStream<Bus> busEvent = KafkaSourceFactory.createKafkaBusSource(env);
        DataStream<String> kafkaStream = KafkaSourceFactory.createKafkaSource(env);
        //Print what we ingested for debugging
        kafkaStream.print();
        //Sink
        kafkaStream.addSink(MySQLSinkFactory.configureSink());

        env.execute("Process Messages");

    }
}