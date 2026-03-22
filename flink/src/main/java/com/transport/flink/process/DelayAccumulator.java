package com.transport.flink.process;

public class DelayAccumulator {
    //Need separate counts for averages
    long arrivalCount = 0;
    long departureCount = 0;

    //Delay metrics accumulated
    long totalDepartureDelay;
    long totalArrivalDelay;

    long minDepartureDelay = Long.MAX_VALUE;
    long maxDepartureDelay = Long.MIN_VALUE;

    long minArrivalDelay = Long.MAX_VALUE;
    long maxArrivalDelay = Long.MIN_VALUE;

}
