package com.fyp.springapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fyp.springapp.mapping.tripupdate.TripUpdateEvent;
import com.fyp.springapp.mapping.tripupdate.TripUpdateEventMapper;
import com.fyp.springapp.mapping.vehicle.VehicleEvent;
import com.fyp.springapp.mapping.vehicle.VehicleEventMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
class PollingService {
    private final WebClient webClient;
    private final VehicleEventMapper vehicleEventMapper;
    private final TripUpdateEventMapper tripUpdateEventMapper;
    private final FilterEventsService filterEventsService;
    private final VehicleEventPublisher vehicleEventPublisher;
    private final TripUpdateEventPublisher tripUpdateEventPublisher;
    private final String vehiclesPath = "/gtfsr/v2/Vehicles";
    private final String tripUpdatePath = "/gtfsr/v2/TripUpdates";
    private final int realTimePollInterval = 60000;

    public PollingService(WebClient webClient, VehicleEventMapper vehicleEventMapper, TripUpdateEventMapper tripUpdateEventMapper, FilterEventsService filterEventsService, VehicleEventPublisher eventPublisher, TripUpdateEventPublisher tripEventPublisher) {
        this.webClient = webClient;
        this.vehicleEventMapper = vehicleEventMapper;
        this.tripUpdateEventMapper = tripUpdateEventMapper;
        this.filterEventsService = filterEventsService;
        this.vehicleEventPublisher = eventPublisher;
        this.tripUpdateEventPublisher = tripEventPublisher;
    }

    @Scheduled(fixedRate = realTimePollInterval)
    public void sendReqToGtfsVehicle() throws JsonProcessingException {
        //Get response
        String response = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(vehiclesPath)
                                .queryParam("format","json")
                                .build()
                )
                .header("Ocp-Apim-Subscription-Key", "{$API_KEY_1}")
                .header("Cache-Control", "no-cache")
                .header("x-api-key","{$API_KEY_2}")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //Map JSON -> DTO -> POJO
        List<VehicleEvent> events = vehicleEventMapper.mapJsonBodyToPojo(response);
        //Get rid of irrelevant data
        List<VehicleEvent> filteredEvents = filterEventsService.filterVehicleEvents(events);
        //For each event, send to Kafka
        for (VehicleEvent event : filteredEvents) {
            //Print statement for debugging
            System.out.println(event);
            vehicleEventPublisher.sendVehicleEventToKafka(event);
        }
    }

    @Scheduled(fixedRate = realTimePollInterval)
    public void sendReqToGtfsTripUpdates() throws JsonProcessingException {
        //Get response
        String response = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(tripUpdatePath)
                                .queryParam("format", "json")
                                .build()
                )
                .header("Ocp-Apim-Subscription-Key", "{$API_KEY_1}")
                .header("Cache-Control", "no-cache")
                .header("x-api-key","{$API_KEY_2}")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //Map JSON -> DTO -> POJO
        List<TripUpdateEvent> events = tripUpdateEventMapper.mapJsonBodyToPojo(response);
        //Get rid of irrelevant data
        List<TripUpdateEvent> filteredEvents = filterEventsService.filterTripUpdateEvents(events);
        //For each event, send to Kafka
        for (TripUpdateEvent event : filteredEvents) {
            //Print statement for debugging
            System.out.println(event);
            tripUpdateEventPublisher.sendTripUpdateEventToKafka(event);
        }
    }
}
