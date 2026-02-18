package com.fyp.springapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
class PollingService {
    private final WebClient webClient;
    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PollingService(WebClient webClient, KafkaTemplate<String, String> kafkaTemplate) {
        this.webClient = webClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10000)
    public void fetchStatus() {
        webClient.get()
                .uri("https://jsonplaceholder.typicode.com")
                .exchangeToMono(response -> {
                    System.out.println(response.statusCode());
                    return response.bodyToMono(String.class);
                })
                //block will wait for the response
                .block();
    }

    @Scheduled(fixedRate = 10000)
    public void fetchJSONBody() {
        String response = webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //Debug
        System.out.println("Response Body:");
        System.out.println(response);
        //Send downstream
        sendResponseToKafka(response);
    }

    public void sendResponseToKafka(String response) {
        kafkaTemplate.send("test-topic-sb", response);
        System.out.println("Sending response to kafka topic");
    }
}
