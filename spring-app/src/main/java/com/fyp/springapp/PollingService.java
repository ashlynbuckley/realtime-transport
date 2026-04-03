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

        List<VehicleEvent> events = vehicleEventMapper.mapJsonBodyToPojo(response);
        List<VehicleEvent> filteredEvents = filterEventsService.filterVehicleEvents(events);

        for (VehicleEvent event : filteredEvents) {
            System.out.println(event);
            vehicleEventPublisher.sendVehicleEventToKafka(event);
        }
    }

    @Scheduled(fixedRate = realTimePollInterval)
    public void sendReqToGtfsTripUpdates() throws JsonProcessingException {
        String response = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(tripUpdatePath)
                                .queryParam("format", "json")
                                .build()
                )
                .header("Ocp-Apim-Subscription-Key", "7af5d298206b4bfc8d205beb38fb5d9d")
                .header("Cache-Control", "no-cache")
                .header("x-api-key","ae1a643563654b2cac3dc2ac307a068d")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        List<TripUpdateEvent> events = tripUpdateEventMapper.mapJsonBodyToPojo(response);
        List<TripUpdateEvent> filteredEvents = filterEventsService.filterTripUpdateEvents(events);

        for (TripUpdateEvent event : filteredEvents) {
            System.out.println(event);
            tripUpdateEventPublisher.sendTripUpdateEventToKafka(event);
        }
    }
}
