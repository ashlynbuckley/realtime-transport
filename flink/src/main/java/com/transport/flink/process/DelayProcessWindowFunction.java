package com.transport.flink.process;

import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class DelayProcessWindowFunction extends ProcessWindowFunction<
        DelayAccumulator, RouteMetric, String, TimeWindow> {

    @Override
    public void process(String routeId, Context context, Iterable<DelayAccumulator> in,
                        Collector<RouteMetric> out) throws Exception {

        DelayAccumulator acc = in.iterator().next();

        long windowStart = context.window().getStart();
        long windowEnd = context.window().getEnd();

        //Compute derived metrics
        //The ternary op ensures you don't try dividing by 0
        double avgArrivalDelay = acc.arrivalCount == 0 ?
                0 : (double)acc.totalArrivalDelay / acc.arrivalCount;
        double avgDepartureDelay = acc.departureCount == 0 ?
                0 : (double) acc.totalDepartureDelay / acc.departureCount;

        long totalDelay = acc.totalArrivalDelay + acc.totalDepartureDelay;

        long delayCount = acc.arrivalCount + acc.departureCount;

        RouteMetric metric = new RouteMetric(
                routeId,
                windowStart,
                windowEnd,
                delayCount,
                acc.maxArrivalDelay,
                acc.minArrivalDelay,
                acc.maxDepartureDelay,
                acc.minDepartureDelay,
                acc.totalDepartureDelay,
                acc.totalArrivalDelay,
                avgArrivalDelay,
                avgDepartureDelay,
                totalDelay
        );
        out.collect(metric);
    }
}
