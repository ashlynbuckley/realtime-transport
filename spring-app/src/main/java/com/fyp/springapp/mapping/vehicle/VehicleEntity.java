package com.fyp.springapp.mapping.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fyp.springapp.mapping.Entity;

public class VehicleEntity extends Entity {
    private Vehicle vehicle;
    @JsonProperty("timestamp")
    private String vehicleTimestamp;

    public VehicleEntity() {
        super();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleTimestamp() {
        return vehicleTimestamp;
    }
    public void setVehicleTimestamp(String vehicleTimestamp) {
        this.vehicleTimestamp = vehicleTimestamp;
    }
}
