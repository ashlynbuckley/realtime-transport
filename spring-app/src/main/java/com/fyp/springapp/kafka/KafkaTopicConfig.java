package com.fyp.springapp.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        //"bootstrap.servers" is the initial conn point for kafka cluster
        config.put("bootstrap.servers", bootstrapAddress);
        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic testTopicSB() {
        return new NewTopic("test-topic-sb", 1, (short) 1);
    }

    @Bean
    public NewTopic vehicleTopic() {
        return new NewTopic("vehicle-topic", 1, (short) 1)
                //retention policy (1 day)
                .configs(Map.of(
                        "retention.ms", "86400000"
                ));
    }

    @Bean
    public NewTopic tripUpdateTopic() {
        return new NewTopic("trip-update-topic", 1, (short) 1)
                //retention policy (1 day)
                .configs(Map.of(
                "retention.ms", "86400000"
                ));
    }
}
