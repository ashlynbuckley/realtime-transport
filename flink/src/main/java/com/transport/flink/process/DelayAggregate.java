package com.transport.flink.process;

import com.transport.flink.process.observation.DelayObservation;
import com.transport.flink.process.observation.DelayType;
import org.apache.flink.api.common.functions.AggregateFunction;

public class DelayAggregate implements AggregateFunction<DelayObservation, DelayAccumulator, DelayAccumulator> {

    @Override
    public DelayAccumulator createAccumulator() {
        return new DelayAccumulator();
    }

    @Override
    public DelayAccumulator add(DelayObservation delayObservation, DelayAccumulator delayAccumulator) {
        if (delayObservation.getDelayType() == DelayType.ARRIVAL) {
            delayAccumulator.totalArrivalDelay += delayObservation.getDelay();
            delayAccumulator.arrivalCount++;
            delayAccumulator.minArrivalDelay = Math.min(delayAccumulator.minArrivalDelay, delayObservation.getDelay());
            delayAccumulator.maxArrivalDelay = Math.max(delayAccumulator.maxArrivalDelay, delayObservation.getDelay());
        }
        else {
            delayAccumulator.totalDepartureDelay += delayObservation.getDelay();
            delayAccumulator.departureCount++;
            delayAccumulator.minDepartureDelay = Math.min(delayAccumulator.minDepartureDelay, delayObservation.getDelay());
            delayAccumulator.maxDepartureDelay = Math.max(delayAccumulator.maxDepartureDelay, delayObservation.getDelay());
        }
        return delayAccumulator;
    }

    @Override
    public DelayAccumulator getResult(DelayAccumulator delayAccumulator) {
        return delayAccumulator;
    }

    @Override
    public DelayAccumulator merge(DelayAccumulator a, DelayAccumulator b) {

        DelayAccumulator r = new DelayAccumulator();

        r.arrivalCount = a.arrivalCount + b.arrivalCount;
        r.departureCount = a.departureCount + b.departureCount;
        r.totalArrivalDelay = a.totalArrivalDelay + b.totalArrivalDelay;
        r.minArrivalDelay = a.minArrivalDelay + b.minArrivalDelay;
        r.maxArrivalDelay = a.maxArrivalDelay + b.maxArrivalDelay;
        r.minDepartureDelay = a.minDepartureDelay + b.minDepartureDelay;
        r.maxDepartureDelay = a.maxDepartureDelay + b.maxDepartureDelay;

        return r;
    }

}
