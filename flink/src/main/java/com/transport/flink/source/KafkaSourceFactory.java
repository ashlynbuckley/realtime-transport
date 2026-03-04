package com.transport.flink.source;

import com.fyp.avro.AvroVehicleEvent;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KafkaSourceFactory {

    public static DataStream<AvroVehicleEvent> createKafkaSource(StreamExecutionEnvironment env) {
        KafkaSource<AvroVehicleEvent> source = KafkaSource.<AvroVehicleEvent>builder()
                .setBootstrapServers("localhost:29092") //kafka broker sending through here
                .setTopics("test-topic-sb")
                .setGroupId("flink-test-group")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(
                        ConfluentRegistryAvroDeserializationSchema
                                .forSpecific(AvroVehicleEvent.class, "http://localhost:8081")
                )
                .build();

        return env.fromSource(
                source,
                WatermarkStrategy.noWatermarks(),
                "Kafka Source"
        );
    }
}