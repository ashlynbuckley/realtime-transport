package com.fyp.springapp.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Trip {
    @JsonProperty("trip_id")
    private String tripId;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("schedule_relationship")
    private String scheduleRelationship;
    @JsonProperty("route_id")
    private String routeId;

    public String getTripId() {
        return tripId;
    }
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getScheduleRelationship() {
        return scheduleRelationship;
    }
    public void setScheduleRelationship(String scheduleRelationship) {
        this.scheduleRelationship = scheduleRelationship;
    }

    public String getRouteId() {
        return routeId;
    }
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
}
