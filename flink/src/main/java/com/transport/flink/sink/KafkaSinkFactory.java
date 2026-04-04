package com.transport.flink.sink;

import com.fyp.core.avro.AvroRouteMetric;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroSerializationSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KafkaSinkFactory {

    public static KafkaSink<AvroRouteMetric> configureKafkaSink(StreamExecutionEnvironment env, String topic) {
        System.out.println("Building sink");
        return KafkaSink.<AvroRouteMetric>builder()
                //Inside Docker
                .setBootstrapServers("localhost:29092")
                .setRecordSerializer(
                    KafkaRecordSerializationSchema.builder()
                            .setTopic(topic)
                            //For schema registry
                            .setValueSerializationSchema(
                                    ConfluentRegistryAvroSerializationSchema.forSpecific(
                                            AvroRouteMetric.class,
                                            topic + "-value",
                                            //Inside Docker
                                            "http://localhost:8081")
                            )
                            .build()
                )
                .build();
    }
}
