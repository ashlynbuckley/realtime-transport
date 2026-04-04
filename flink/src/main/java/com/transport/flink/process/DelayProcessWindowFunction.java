package com.transport.flink.process;

import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * Window function that outputs RouteMetric at the end of accumulating data and deriving metrics at the end of a window of time
 */
public class DelayProcessWindowFunction extends ProcessWindowFunction<
        DelayAccumulator, RouteMetric, String, TimeWindow> {

    @Override
    public void process(String routeId, Context context, Iterable<DelayAccumulator> in,
                        Collector<RouteMetric> out) throws Exception {
        String windowType;
        DelayAccumulator acc = in.iterator().next();

        long windowStart = context.window().getStart();
        long windowEnd = context.window().getEnd();
        long windowTimeMs = windowEnd - windowStart;
        //retain which window granularity it's been created for - needed for keying in db
        if (windowTimeMs == 5 * 60 * 1000) {
            windowType = "5m";
        }
        else if (windowTimeMs == 30 * 60 * 1000) {
            windowType = "30m";
        }
        else if (windowTimeMs == 60 * 60 * 1000) {
            windowType = "60m";
        }
        else {
            windowType = "UNKNOWN";
        }

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
                windowType,
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
