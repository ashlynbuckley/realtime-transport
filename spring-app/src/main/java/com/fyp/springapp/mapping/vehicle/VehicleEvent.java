package com.fyp.springapp.mapping.vehicle;

/**
 * Internal representation of vehicle events
 */
public class VehicleEvent{
    private String tripId;
    private String startTime;
    private String startDate;
    private String scheduleRelationship;
    private String routeId;
    private String vehicleTimestamp;

    public VehicleEvent(String tripId, String startTime, String startDate, String scheduleRelationship,
                        String routeId, String vehicleTimestamp) {
        this.tripId = tripId;
        this.startTime = startTime;
        this.startDate = startDate;
        this.scheduleRelationship = scheduleRelationship;
        this.routeId = routeId;
        this.vehicleTimestamp = vehicleTimestamp;
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

    public String getVehicleTimestamp() {
        return vehicleTimestamp;
    }
    public void setVehicleTimestamp(String vehicleTimestamp) {
        this.vehicleTimestamp = vehicleTimestamp;
    }
}
