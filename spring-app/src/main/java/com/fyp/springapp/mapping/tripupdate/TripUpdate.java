package com.fyp.springapp.mapping.tripupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fyp.springapp.mapping.Trip;

import java.util.List;

public class TripUpdate {
    private Trip trip;
    private List<StopTimeUpdate> stopTimeUpdate;
    @JsonProperty("timestamp")
    private String tripUpdateTimestamp;

    public Trip getTrip() {
        return trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public List<StopTimeUpdate> getStopTimeUpdate() {
        return stopTimeUpdate;
    }
    public void setStopTimeUpdate(List<StopTimeUpdate> stopTimeUpdate) {
        this.stopTimeUpdate = stopTimeUpdate;
    }

    public String getTripUpdateTimestamp() {
        return tripUpdateTimestamp;
    }
    public void setTripUpdateTimestamp(String tripUpdateTimestamp) {
        this.tripUpdateTimestamp = tripUpdateTimestamp;
    }

}
