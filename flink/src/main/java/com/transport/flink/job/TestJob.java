package com.transport.flink.job;

import com.fyp.core.avro.AvroRouteMetric;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroSerializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TestJob {
    public static void main(String[] args) throws Exception {
        KafkaSink<AvroRouteMetric> sink = KafkaSink.<AvroRouteMetric>builder()
                .setBootstrapServers("localhost:29092")
                .setRecordSerializer(
                        KafkaRecordSerializationSchema.builder()
                                .setTopic("route-metric-topic")
                                .setValueSerializationSchema(
                                        ConfluentRegistryAvroSerializationSchema.forSpecific(
                                                AvroRouteMetric.class,
                                                "route-metric-topic-value",
                                                "http://localhost:8081"
                                        )
                                )
                                .build()
                )
                .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<AvroRouteMetric> stream = env.fromElements(
                AvroRouteMetric.newBuilder()
                        .setRouteId("test-route")
                        .setWindowStart(System.currentTimeMillis())
                        .setWindowEnd(System.currentTimeMillis() + 300_000)
                        .setWindowType("5m")
                        .setDelayCount(1)
                        .setMaxArrivalDelay(10)
                        .setMinArrivalDelay(5)
                        .setMaxDepartureDelay(15)
                        .setMinDepartureDelay(7)
                        .setTotalDepartureDelay(15)
                        .setTotalArrivalDelay(10)
                        .setAvgArrivalDelay(10)
                        .setAvgDepartureDelay(15)
                        .setTotalOverallDelay(25)
                        .build()
        );

        stream.sinkTo(sink);
        env.execute("Produce first message to register schema");
    }
}
