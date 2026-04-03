package com.transport.flink.job;

import com.fyp.core.avro.AvroRouteMetric;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkMySQLJob {
    public static void main(String[] args) throws Exception {
        final String ROUTE_METRIC_TOPIC = "route-metric-topic";
        //Environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //Kafka source
        KafkaSource<AvroRouteMetric> source = KafkaSource.<AvroRouteMetric>builder()
                .setBootstrapServers("localhost:29092")
                .setTopics(ROUTE_METRIC_TOPIC)
                .setGroupId("source-for-mysql")
                .setValueOnlyDeserializer(
                        ConfluentRegistryAvroDeserializationSchema
                                .forSpecific(AvroRouteMetric.class, "http://localhost:8081")
                )
                .build();

        DataStream<AvroRouteMetric> stream = env.fromSource(
                source,
                WatermarkStrategy.noWatermarks(),
                "Kafka Source For MySQL"
        );

        stream.addSink(
                JdbcSink.sink("INSERT INTO trip_events (" +
                                "routeId, windowStart, windowEnd, windowType, delayCount, " +
                                "maxArrivalDelay, minArrivalDelay, maxDepartureDelay, minDepartureDelay, totalDepartureDelay, " +
                                "totalArrivalDelay, avgArrivalDelay, avgDepartureDelay, totalOverallDelay) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                                "ON DUPLICATE KEY UPDATE" +
                                "  delayCount = VALUES(delayCount), " +
                                "  maxArrivalDelay = VALUES(maxArrivalDelay), " +
                                "  minArrivalDelay = VALUES(minArrivalDelay), " +
                                "  maxDepartureDelay = VALUES(maxDepartureDelay), " +
                                "  minDepartureDelay = VALUES(minDepartureDelay), " +
                                "  totalDepartureDelay = VALUES(totalDepartureDelay), " +
                                "  totalArrivalDelay = VALUES(totalArrivalDelay), " +
                                "  avgArrivalDelay = VALUES(avgArrivalDelay), " +
                                "  avgDepartureDelay = VALUES(avgDepartureDelay), " +
                                "  totalOverallDelay = VALUES(totalOverallDelay);",
                        (ps, event) -> {
                            ps.setString(1, event.getRouteId().toString());
                            ps.setLong(2, event.getWindowStart());
                            ps.setLong(3, event.getWindowEnd());
                            ps.setString(4, event.getWindowType().toString());
                            ps.setLong(5, event.getDelayCount());
                            ps.setLong(6, event.getMaxArrivalDelay());
                            ps.setLong(7, event.getMinArrivalDelay());
                            ps.setLong(8, event.getMaxDepartureDelay());
                            ps.setLong(9, event.getMinDepartureDelay());
                            ps.setLong(10, event.getTotalDepartureDelay());
                            ps.setLong(11, event.getTotalArrivalDelay());
                            ps.setDouble(12, event.getAvgArrivalDelay());
                            ps.setDouble(13, event.getAvgDepartureDelay());
                            ps.setLong(14, event.getTotalOverallDelay());
                        },
                        JdbcExecutionOptions.builder()
                                .withBatchSize(500)
                                .withBatchIntervalMs(200)
                                .withMaxRetries(3)
                                .build(),
                        new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                                .withUrl("jdbc:mysql://localhost:3307/flink_test")
                                .withDriverName("com.mysql.cj.jdbc.Driver")
                                .withUsername("flink")
                                .withPassword("flink")
                                .build()
                ));

        env.execute("To MySQL");
    }



}
