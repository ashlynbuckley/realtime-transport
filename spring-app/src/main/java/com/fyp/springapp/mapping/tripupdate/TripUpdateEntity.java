package com.fyp.springapp.mapping.tripupdate;

import com.fyp.springapp.mapping.Entity;

public class TripUpdateEntity extends Entity {
    private TripUpdate tripUpdate;
    public TripUpdateEntity() {
        super();
    }

    public TripUpdate getTripUpdate() {
        return tripUpdate;
    }
    public void setTripUpdate(TripUpdate tripUpdate) {
        this.tripUpdate = tripUpdate;
    }
}
