package com.fyp.springapp.mapping.tripupdate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Arrival {
    private int delay;

    public int getDelay() {
        return delay;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }
}
