package com.transport.flink.process.observation;

public class DelayObservation {
    String routeId;
    String tripId;
    DelayType delayType;
    Integer delay;
    long timestamp;

    public DelayObservation(String routeId, String tripId, DelayType delayType, Integer delay, String timestamp) {
        this.routeId = routeId;
        this.tripId = tripId;
        this.delayType = DelayType.ARRIVAL;
        this.delay = delay;
        this.timestamp = Long.parseLong(timestamp) * 1000L;
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
    public DelayType getDelayType() {
        return delayType;
    }
    public void setDelayType(DelayType delayType) {
        this.delayType = delayType;
    }
    public Integer getDelay() {
        return delay;
    }
    public void setDelay(Integer delay) {
        this.delay = delay;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
