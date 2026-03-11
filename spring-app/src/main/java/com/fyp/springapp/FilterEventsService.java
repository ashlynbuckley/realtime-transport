package com.fyp.springapp;

import com.fyp.springapp.mapping.tripupdate.TripUpdateEvent;
import com.fyp.springapp.mapping.vehicle.VehicleEvent;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilterEventsService {
    Set<String> TRACKED_ROUTES;

    public FilterEventsService() {
        //TODO: Extract route_ids from static
        TRACKED_ROUTES = new HashSet<>();
        TRACKED_ROUTES.add("5401_125999"); //401
        TRACKED_ROUTES.add("5401_126000"); //402
        TRACKED_ROUTES.add("5401_126001");
        TRACKED_ROUTES.add("5401_126002");
        TRACKED_ROUTES.add("5401_126003");
        TRACKED_ROUTES.add("5401_126004"); //409
        TRACKED_ROUTES.add("5421_116109"); //410
        TRACKED_ROUTES.add("5421_116110");
        TRACKED_ROUTES.add("5421_116111"); //412
    }

    public List<VehicleEvent> filterVehicleEvents(List<VehicleEvent> events) {
        return events.stream()
                .filter(event ->
                        TRACKED_ROUTES.contains(event.getRouteId())
                )
                .toList();
    }

    public List<TripUpdateEvent> filterTripUpdateEvents(List<TripUpdateEvent> events) {
        return events.stream()
                .filter(event ->
                        TRACKED_ROUTES.contains(event.getRouteId())
                )
                .toList();
    }
}
