package com.transport.flink.process;

public class DelayAccumulator {
    //Counting how many times we have a delay
    long count = 0;

    //Delay metrics
    long totalDepartureDelay = 0;
    long totalArrivalDelay = 0;
    long totalOverallDelay = 0;

    long minDepartureDelay = Long.MAX_VALUE;
    long maxDepartureDelay = Long.MIN_VALUE;

    long minArrivalDelay = Long.MAX_VALUE;
    long maxArrivalDelay = Long.MIN_VALUE;

    long avgDepartureDelay = 0;
    long avgArrivalDelay = 0;

}
