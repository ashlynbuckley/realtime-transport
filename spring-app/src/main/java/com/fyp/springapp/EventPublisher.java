package com.fyp.springapp;

import com.fyp.avro.AvroVehicleEvent;
import com.fyp.springapp.mapping.VehicleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    @Autowired
    private final KafkaTemplate<String, com.fyp.avro.AvroVehicleEvent> kafkaTemplate;

    private static final String TOPIC = "test-topic-sb";

    public EventPublisher(KafkaTemplate<String, com.fyp.avro.AvroVehicleEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEventToKafka(VehicleEvent event) {
        AvroVehicleEvent avroEvent = serialiseEvent(event);
        kafkaTemplate.send(TOPIC, avroEvent);
        System.out.println("Event sent to Kafka topic");
    }

    private AvroVehicleEvent serialiseEvent(VehicleEvent event) {
        AvroVehicleEvent avroEvent = AvroVehicleEvent.newBuilder()
                .setTripId(event.getTripId())
                .setStartTime(event.getStartTime())
                .setStartDate(event.getStartDate())
                .setScheduleRelationship(event.getScheduleRelationship())
                .setRouteId(event.getRouteId())
                .setVehicleTimestamp(event.getVehicleTimestamp())
                .build();

        System.out.println("Serialised");
        return avroEvent;
    }
}
