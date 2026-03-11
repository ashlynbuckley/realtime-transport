package com.fyp.springapp.mapping.tripupdate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StopTimeUpdate {
    @JsonProperty("stop_sequence")
    private String stopSequence;
    @JsonProperty("arrival")
    private Arrival arrival;
    @JsonProperty("departure")
    private Departure departure;
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
    public Departure getDeparture() {
        return departure;
    }
    public void setDeparture(Departure departure) {
        this.departure = departure;
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
