package com.fyp.springapp.mapping.tripupdate;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Arrival {
    @JsonProperty("delay")
    private Integer arrivalDelay;
    private String time;
    private Integer uncertainty;

    public Integer getArrivalDelay() {
        return arrivalDelay;
    }
    public void setArrival(Integer arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Integer getUncertainty() {
        return uncertainty;
    }
    public void setUncertainty(Integer uncertainty) {
        this.uncertainty = uncertainty;
    }
}
