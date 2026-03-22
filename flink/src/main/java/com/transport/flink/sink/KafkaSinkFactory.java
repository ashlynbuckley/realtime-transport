package com.transport.flink.sink;

import org.apache.flink.connector.kafka.sink.KafkaSink;

public class KafkaSinkFactory {

    public static KafkaSink configureKafkaSink() {
        System.out.println("Creating Kafka Sink");
//        return KafkaSink.sink();
    }
}
