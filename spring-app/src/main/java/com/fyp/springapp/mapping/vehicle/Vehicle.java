package com.fyp.springapp.mapping.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fyp.springapp.mapping.Trip;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {
    private Trip trip;
    private String timestamp;

    public Trip getTrip() {
        return trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

