package com.transport.flink.process;

/**
 * (DTO) POJO of each vehicle currently active, extracted from response
 */
public class Bus {
    //fields
    public String id;
    public String tripId;
    public String startTime;
    //epoch
    public String startDate;
    public String scheduleRelationship;
    public String routeId;
    public String timestamp;

    //cons using json body converted to POJO
    public Bus(String id, String tripId, String startTime, String startDate, String scheduleRelationship, String routeId, String timestamp) {
        this.id = id;
        this.tripId = tripId;
        this.startTime = startTime;
        this.startDate = startDate;
        this.scheduleRelationship = scheduleRelationship;
        this.routeId = routeId;
        this.timestamp = timestamp;
        //convert to POJO
        //convert fields from String to specifics, e.g. DateTime
        //assign it to the route it fulfils
    }
//    private void assignRoute() {
//        //TODO: get routeId, find Route that matches
//        //if there is an object of route with the routeId of the bus, append if not create and append
//        Route r;
//        if (r.getRouteId().equals(routeId)) {
//            r.getVehiclesInRoute().add(this);
//        }
//        else {
//            Route newRoute = new Route(routeId);
//            newRoute.getVehiclesInRoute().add(this);
//        }
//
//        //if route does not exist, create it
//    }

    //Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
