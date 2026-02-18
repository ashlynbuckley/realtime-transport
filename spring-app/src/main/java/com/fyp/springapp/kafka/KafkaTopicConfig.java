package com.fyp.springapp.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

public class KafkaTopicConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        //TODO: configure actual app variables
        config.put("bootstrap.servers", "localhost:9092");
        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("test-topic-sp", 1, (short) 1);
    }
}
