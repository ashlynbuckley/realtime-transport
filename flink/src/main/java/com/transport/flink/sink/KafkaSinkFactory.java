package com.transport.flink.sink;

import com.transport.flink.process.RouteMetric;
import org.apache.flink.connector.kafka.sink.KafkaSink;

public class KafkaSinkFactory {

    public static KafkaSink<RouteMetric> configureKafkaSink() {

        return KafkaSink.<RouteMetric>builder()
                .setBootstrapServers("localhost:29092")
                .setRecordSerializer()
                .build();
    }
}
