package com.fyp.springapp.mapping.tripupdate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StopTimeUpdate {
    @JsonProperty("stop_sequence")
    private String stopSequence;
    private Arrival arrival;
    @JsonProperty("stop_id")
    private String stopId;
    @JsonProperty("schedule_relationship")
    private String scheduleRelationship;

    public String getStopSequence() {
        return stopSequence;
    }
    public void setStopSequence(String stopSequence) {
        this.stopSequence = stopSequence;
    }
    public Arrival getArrival() {
        return arrival;
    }
    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }
    public String getStopId() {
        return stopId;
    }
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }
    public String getScheduleRelationship() {
        return scheduleRelationship;
    }
    public void setScheduleRelationship(String scheduleRelationship) {
        this.scheduleRelationship = scheduleRelationship;
    }
}
