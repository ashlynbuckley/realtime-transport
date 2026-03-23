package com.transport.flink.process;

import com.fyp.avro.AvroRouteMetric;
import org.apache.flink.api.common.functions.MapFunction;

public class MetricMapper implements MapFunction<RouteMetric, AvroRouteMetric> {

    @Override
    public AvroRouteMetric map(RouteMetric routeMetric) throws Exception {
        return new AvroRouteMetric().newBuilder()
                .setRouteId(routeMetric.routeId)
                .setWindowStart(routeMetric.windowStart)
                .setWindowEnd(routeMetric.windowEnd)
                .setDelayCount(routeMetric.delayCount)
                .setMaxArrivalDelay(routeMetric.maxArrivalDelay)
                .setMinArrivalDelay(routeMetric.minArrivalDelay)
                .setMaxDepartureDelay(routeMetric.maxDepartureDelay)
                .setMinDepartureDelay(routeMetric.minDepartureDelay)
                .setTotalDepartureDelay(routeMetric.totalDepartureDelay)
                .setTotalArrivalDelay(routeMetric.totalArrivalDelay)
                .setAvgArrivalDelay(routeMetric.avgArrivalDelay)
                .setAvgDepartureDelay(routeMetric.avgDepartureDelay)
                .setTotalOverallDelay(routeMetric.totalOverallDelay)
                .build();
    }
}
