package com.fyp.springapp;

import com.fyp.springapp.mapping.VehicleEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterEventsService {
    Set<String> TRACKED_ROUTES;

    public FilterEventsService(Set<String> TRACKED_ROUTES) {
        this.TRACKED_ROUTES = TRACKED_ROUTES;
    }

    public List<VehicleEvent> filterEvents(List<VehicleEvent> events) {
        return events.stream()
                .filter(event ->
                        TRACKED_ROUTES.contains(event.getRouteId())
                )
                .toList();
    }
}
