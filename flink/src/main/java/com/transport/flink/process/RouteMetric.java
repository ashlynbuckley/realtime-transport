package com.transport.flink.process;

public class RouteMetric {
    String routeId;
    long windowStart;
    long windowEnd;
    //for keying in database
    String windowType;

    //Incremental
    long delayCount;

    long maxArrivalDelay;
    long minArrivalDelay;
    long maxDepartureDelay;
    long minDepartureDelay;

    long totalDepartureDelay;
    long totalArrivalDelay;

    //Derived
    double avgArrivalDelay;
    double avgDepartureDelay;
    long totalOverallDelay;

    public RouteMetric(String routeId, long windowStart, long windowEnd, String windowType, long delayCount, long maxArrivalDelay,
                       long minArrivalDelay, long maxDepartureDelay, long minDepartureDelay, long totalDepartureDelay,
                       long totalArrivalDelay, double avgArrivalDelay, double avgDepartureDelay, long totalOverallDelay) {
        this.routeId = routeId;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
        this.windowType = windowType;
        this.delayCount = delayCount;
        this.maxArrivalDelay = maxArrivalDelay;
        this.minArrivalDelay = minArrivalDelay;
        this.maxDepartureDelay = maxDepartureDelay;
        this.minDepartureDelay = minDepartureDelay;
        this.totalDepartureDelay = totalDepartureDelay;
        this.totalArrivalDelay = totalArrivalDelay;
        this.avgArrivalDelay = avgArrivalDelay;
        this.avgDepartureDelay = avgDepartureDelay;
        this.totalOverallDelay = totalOverallDelay;
    }
}
