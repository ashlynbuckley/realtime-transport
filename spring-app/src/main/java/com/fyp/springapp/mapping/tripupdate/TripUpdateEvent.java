package com.fyp.springapp.mapping.tripupdate;

import java.util.List;

public class TripUpdateEvent {
    //TODO: will most likely trim this down once i know what i dont need bc it's already in VehicleEvent
    private String tripId;
    private String startTime;
    private String startDate;
    //from trip
    private String scheduleRelationship;
    private String routeId;
    private String tripUpdateTimestamp;
    private List<StopTimeUpdatePOJO> updates;

    public TripUpdateEvent(String tripId, String startTime, String startDate, String scheduleRelationship,
                           String routeId, String tripUpdateTimestamp, List<StopTimeUpdatePOJO> updates) {
        this.tripId = tripId;
        this.startTime = startTime;
        this.startDate = startDate;
        this.scheduleRelationship = scheduleRelationship;
        this.routeId = routeId;
        this.tripUpdateTimestamp = tripUpdateTimestamp;
        this.updates = updates;

    }
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

    public String getTripUpdateTimestamp() {
        return tripUpdateTimestamp;
    }
    public void setTripUpdateTimestamp(String tripUpdateTimestamp) {
        this.tripUpdateTimestamp = tripUpdateTimestamp;
    }

    public List<StopTimeUpdatePOJO> getUpdates() {
        return updates;
    }
    public void setUpdates(List<StopTimeUpdatePOJO> updates) {
        this.updates = updates;
    }
}
