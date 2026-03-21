package com.transport.flink.process;

public class ArrivalDelayAccumulator {

    public void add(int delay) {
        count++;
        totalDepartureDelay += delay;
        minDelay = delay < minDelay ? delay : minDelay;
        maxDelay = delay > maxDelay ? delay : maxDelay;
    }
}
