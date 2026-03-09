package com.fyp.springapp.mapping.tripupdate;

public class TripUpdateEvent {
    //TODO: will most likely trim this down once i know what i dont need bc it's already in VehicleEvent
    private String tripId;
    private String startTime;
    private String startDate;
    //from trip
    private String scheduleRelationship;
    private String routeId;
    private String tripUpdateTimestamp;
    private String stopSequence;
    private int delay;
    private String stopId;
    //from stop_time_update - changes are reflected in this one
    private String stopTimeUpdateScheduleRelationship;

    public TripUpdateEvent(String tripId, String startTime, String startDate, String scheduleRelationship,
                           String routeId, String tripUpdateTimestamp, String stopSequence, int delay,
                           String stopId, String stopTimeUpdateScheduleRelationship) {
        this.tripId = tripId;
        this.startTime = startTime;
        this.startDate = startDate;
        this.scheduleRelationship = scheduleRelationship;
        this.routeId = routeId;
        this.tripUpdateTimestamp = tripUpdateTimestamp;
        this.stopSequence = stopSequence;
        this.delay = delay;
        this.stopId = stopId;
        this.stopTimeUpdateScheduleRelationship = stopTimeUpdateScheduleRelationship;

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

    public String getStopSequence() {
        return stopSequence;
    }
    public void setStopSequence(String stopSequence) {
        this.stopSequence = stopSequence;
    }
    public int getDelay() {
        return delay;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }
    public String getStopId() {
        return stopId;
    }
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }
    public String getStopTimeUpdateScheduleRelationship() {
        return stopTimeUpdateScheduleRelationship;
    }
    public void setStopTimeUpdateScheduleRelationship(String stopTimeUpdateScheduleRelationship) {
        this.stopTimeUpdateScheduleRelationship = stopTimeUpdateScheduleRelationship;
    }
}
