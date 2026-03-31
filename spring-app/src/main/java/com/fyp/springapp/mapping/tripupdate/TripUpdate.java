package com.fyp.springapp.mapping.tripupdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fyp.springapp.mapping.Trip;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripUpdate {
    private Trip trip;
    @JsonProperty("stop_time_update")
    private List<StopTimeUpdate> stopTimeUpdate = new ArrayList<>();
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
