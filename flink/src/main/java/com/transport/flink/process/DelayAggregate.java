package com.transport.flink.process;

import com.fyp.avro.AvroTripUpdateEvent;
import org.apache.flink.api.common.functions.AggregateFunction;

public class DelayAggregate implements AggregateFunction<AvroTripUpdateEvent, DelayAccumulator, RouteMetric> {

    @Override
    public DelayAccumulator createAccumulator() {
        return new DelayAccumulator();
    }

    @Override
    public DelayAccumulator add(AvroTripUpdateEvent avroTripUpdateEvent, DelayAccumulator delayAccumulator) {
        return null;
    }

    @Override
    public RouteMetric getResult(DelayAccumulator delayAccumulator) {
        RouteMetric metric = new RouteMetric();
        //TODO: Fill values
        return metric;
    }

    @Override
    public DelayAccumulator merge(DelayAccumulator delayAccumulator, DelayAccumulator acc1) {
        return null;
    }

}
