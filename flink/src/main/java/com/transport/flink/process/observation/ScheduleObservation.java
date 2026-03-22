package com.transport.flink.process.observation;

public class ScheduleObservation {
    String routeId;
    String tripId;
    int cancelledCount;

    public ScheduleObservation(String routeId, String tripId) {
        this.routeId = routeId;
        this.tripId = tripId;
    }

    public String getRouteId() {
        return routeId;
    }
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
    public String getTripId() {
        return tripId;
    }
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
    public int getCancelledCount() {
        return cancelledCount;
    }
    public void setCancelledCount(int cancelledCount) {
        this.cancelledCount = cancelledCount;
    }
    public void incrementCancelledCount() {
        this.cancelledCount++;
    }
}
