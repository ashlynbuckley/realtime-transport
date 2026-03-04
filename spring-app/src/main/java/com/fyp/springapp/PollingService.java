package com.fyp.springapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fyp.springapp.mapping.VehicleEvent;
import com.fyp.springapp.mapping.VehicleEventMapper;
import com.fyp.springapp.FilterEventsService;
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
//    private final KafkaTemplate<String, com.fyp.avro.AvroVehicleEvent> kafkaTemplate;
    private final VehicleEventMapper mapper;
//    private static final String TOPIC = "test-topic-sb";
    private final FilterEventsService filterEventsService;
    private final EventPublisher eventPublisher;

//    private final String baseApiUrl = "https://api.nationaltransport.ie";
    private final String vehiclesPath = "/gtfsr/v2/Vehicles";
    private final int realTimePollInterval = 30000;
    //once per week?
    private final long staticPollInterval = 604800000;

    public PollingService(WebClient webClient, VehicleEventMapper mapper, FilterEventsService filterEventsService, EventPublisher eventPublisher) {
        this.webClient = webClient;
        this.mapper = mapper;
        this.filterEventsService = filterEventsService;
        this.eventPublisher = eventPublisher;
    }

//    @Scheduled(fixedRate = realTimePollInterval)
//    public void fetchStatus() {
//        webClient.get()
//                .uri("https://jsonplaceholder.typicode.com")
//                .exchangeToMono(response -> {
//                    System.out.println(response.statusCode());
//                    return response.bodyToMono(String.class);
//                })
//                //block will wait for the response
//                .block();
//    }

//    @Scheduled(fixedRate = realTimePollInterval)
//    public void fetchJSONBody() {
//        VehicleEvent response = webClient.get()
//                .uri("https://jsonplaceholder.typicode.com/posts/1")
//                .retrieve()
//                .bodyToMono(VehicleEvent.class)
//                .block();
//        //Debug
//        System.out.println("Response Body:");
//        System.out.println(response);
//        //Send downstream
////        sendResponseToKafka(response);
//    }

    @Scheduled(fixedRate = realTimePollInterval)
    public void sendReqToGTFSRealtime() throws JsonProcessingException {
        String response = webClient.get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path(vehiclesPath)
                                        .queryParam("format","json")
                                        .build()
                        )
                        .header("Ocp-Apim-Subscription-Key", "7af5d298206b4bfc8d205beb38fb5d9d")
                        .header("Cache-Control", "no-cache")
                        .header("x-api-key","ae1a643563654b2cac3dc2ac307a068d")
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

        List<VehicleEvent> events = mapper.mapJsonBodyToPojo(response);
        List<VehicleEvent> filteredEvents = filterEventsService.filterEvents(events);

        for (VehicleEvent event : filteredEvents) {
            System.out.println(event);
            eventPublisher.sendEventToKafka(event);
        }
    }

//    private void sendResponseToKafka(String response) {
//        kafkaTemplate.send(TOPIC, response);
//        System.out.println("Sending response to kafka topic");
//    }
}
