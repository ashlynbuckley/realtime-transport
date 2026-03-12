package com.fyp.springapp;

import com.fyp.avro.AvroStopTimeUpdate;
import com.fyp.avro.AvroTripUpdateEvent;
import com.fyp.springapp.mapping.tripupdate.StopTimeUpdatePOJO;
import com.fyp.springapp.mapping.tripupdate.TripUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TripUpdateEventPublisher {

    @Autowired
    private final KafkaTemplate<String, com.fyp.avro.AvroTripUpdateEvent> kafkaTemplate;

    private static final String TRIP_TOPIC = "trip-update-topic";

    public TripUpdateEventPublisher(KafkaTemplate<String, AvroTripUpdateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTripUpdateEventToKafka(TripUpdateEvent event) {
        AvroTripUpdateEvent avroEvent = serialiseTripUpdateEvent(event);
        kafkaTemplate.send(TRIP_TOPIC, avroEvent);
        System.out.println("Event sent to Kafka topic");
    }

    private AvroTripUpdateEvent serialiseTripUpdateEvent(TripUpdateEvent event) {
        //Check for null trip ID
        if (event.getTripId() == null) {
            return null;
        }

        List<AvroStopTimeUpdate> avroUpdates = new ArrayList<>();

        //For every StopTimeUpdatePOJO in this event, convert it to AvroStopTimeUpdate
        for (StopTimeUpdatePOJO pojoUpdate : event.getUpdates()) {
            if (pojoUpdate == null) {
                continue;
            }
            AvroStopTimeUpdate avroUpdate = AvroStopTimeUpdate.newBuilder()
                    .setStopSequence(pojoUpdate.getStopSequence())
                    .setADelay(pojoUpdate.getaDelay())
                    .setDDelay(pojoUpdate.getdDelay())
                    .setStopId(pojoUpdate.getStopId())
                    .setStopTimeUpdateScheduleRelationship(pojoUpdate.getStopTimeUpdateScheduleRelationship())
                    .build();
            avroUpdates.add(avroUpdate);
        }

        AvroTripUpdateEvent avroEvent = AvroTripUpdateEvent.newBuilder()
                .setTripId(event.getTripId())
                .setStartTime(event.getStartTime())
                .setStartDate(event.getStartDate())
                .setScheduleRelationship(event.getScheduleRelationship())
                .setRouteId(event.getRouteId())
                .setTripUpdateTimestamp(event.getTripUpdateTimestamp())
                .setStopTimeUpdates(avroUpdates)
                .build();

        //Returns a SpecificRecord
        //System.out.println(avroEvent.toString());
        return avroEvent;
    }
}
