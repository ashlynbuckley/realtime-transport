package com.transport.flink.source;

import com.transport.flink.Bus;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KafkaSourceFactory {

    public static DataStream<String> createKafkaSource(StreamExecutionEnvironment env) {
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers("localhost:9092") //kafka broker sending through here
                .setTopics("test-topic-sb")
                .setGroupId("flink-test-group")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        return env.fromSource(
                source,
                WatermarkStrategy.noWatermarks(),
                "Kafka Source"
        );
    }

    public static DataStream<Bus> createKafkaBusSource(StreamExecutionEnvironment env) {
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers("localhost:9092") //kafka broker sending through here
                .setTopics("test-topic-sb")
                .setGroupId("flink-test-group")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        return env.fromSource(
                source,
                WatermarkStrategy.noWatermarks(),
                "Kafka Source"
        );
    }
}