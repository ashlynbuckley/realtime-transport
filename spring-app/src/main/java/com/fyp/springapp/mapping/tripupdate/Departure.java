package com.fyp.springapp.mapping.tripupdate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Departure {
    @JsonProperty("delay")
    private Integer departureDelay;
    private String time;

    public Integer getDepartureDelay() {
        return departureDelay;
    }
    public void setDepartureDelay(Integer departureDelay) {
        this.departureDelay = departureDelay;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
