package com.fyp.springapp;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
class PollingService {
    private final WebClient webClient;
//    private final KafkaTemplate<String, String> kafkaTemplate;

    public PollingService(WebClient webClient) {
        this.webClient = webClient;
    }

    //fixedRate is in ms
    @Scheduled(fixedRate = 5000)
    public void fetchAndSendToKafka() {
        String response = webClient.get()
                .uri("https://jsonplaceholder.typicode.com")
                .retrieve()
                //mono=one at a time
                .bodyToMono(String.class)
                //makes this a blocking operation
                .block();
//        kafkaTemplate.send("json", response);
        System.out.println("Received response: " + response);
    }
}
