package com.fyp.springapp;

import com.fyp.springapp.mapping.VehicleEvent;
import com.fyp.springapp.mapping.VehicleEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
class PollingService {
    //GET https://api.nationaltransport.ie/gtfsr/v2/Vehicles?format=json
    private final WebClient webClient;
    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private VehicleEventMapper mapper;
    //will be something more meaningful later
    private static final String TOPIC = "test-topic-sb";
    private VehicleEventMapper responseMapper;
    private FilterEventsService filterEventsService;

//    private final String baseApiUrl = "https://api.nationaltransport.ie";
    private final String vehiclesPath = "/gtfsr/v2/Vehicles";
    private final int realTimePollInterval = 10000;
    //once per week?
    private final long staticPollInterval = 604800000;

    public PollingService(WebClient webClient, KafkaTemplate<String, String> kafkaTemplate) {
        this.webClient = webClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = realTimePollInterval)
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

    @Scheduled(fixedRate = realTimePollInterval)
    public void fetchJSONBody() {
        VehicleEvent response = webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .retrieve()
                .bodyToMono(VehicleEvent.class)
                .block();
        //Debug
        System.out.println("Response Body:");
        System.out.println(response);
        //Send downstream
//        sendResponseToKafka(response);
    }

    @Scheduled(fixedRate = realTimePollInterval)
    public void sendReqToGTFSRealtime() {
        //GET https://api.nationaltransport.ie/gtfsr/v2/Vehicles?format=json
        String response = webClient.get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path(vehiclesPath)
                                        .queryParam("format","json")
                                        .build()
                        )
                        .header("Ocp-Apim-Subscription-Key", "")
                        .header("Cache-Control", "no-cache")
                        .header("x-api-key","")
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

        //For Debug
//        System.out.println(response);

        List<VehicleEvent> events = responseMapper.mapJsonBodyToPojo(response);
        List<VehicleEvent> filteredEvents = filterEventsService.filterEvents(events);

        for (VehicleEvent event : filteredEvents) {
            System.out.println(event);
            sendEventToKafka(event);
        }
    }

    private void sendResponseToKafka(String response) {
        kafkaTemplate.send(TOPIC, response);
        System.out.println("Sending response to kafka topic");
    }

    private void sendEventToKafka(VehicleEvent event) {
        //TODO: Serialise to byte format and send the relevant VehicleEvents
//        kafkaTemplate.send(TOPIC, response);
        System.out.println("Sending events to kafka topic");
    }
}
