package com.transport.flink.source;

import org.apache.avro.specific.SpecificRecord;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KafkaSourceFactory {

    private static final String TRIP_TOPIC = "trip-update-topic";
    private static final String VEHICLE_TOPIC = "vehicle-topic";

    public static <T extends SpecificRecord> DataStream<T> createKafkaSource(StreamExecutionEnvironment env, String topic,
                                                                                            String groupId, Class<T> avroClass) {
        KafkaSource<T> source = KafkaSource.<T>builder()
                .setBootstrapServers("localhost:29092") //kafka broker sending through here
                .setTopics(topic)
                .setGroupId(groupId)
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(
                        ConfluentRegistryAvroDeserializationSchema
                                .forSpecific(avroClass, "http://localhost:8081")
                )
                .build();

        return env.fromSource(
                source,
                WatermarkStrategy.noWatermarks(),
                "Kafka Source"
        );
    }
}