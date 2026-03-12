package com.fyp.springapp;

import com.fyp.avro.AvroVehicleEvent;
import com.fyp.springapp.mapping.vehicle.VehicleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class VehicleEventPublisher {

    @Autowired
    private final KafkaTemplate<String, com.fyp.avro.AvroVehicleEvent> kafkaTemplate;

    private static final String TOPIC = "test-topic-sb";
    private static final String VEHICLE_TOPIC = "vehicle-topic";

    public VehicleEventPublisher(KafkaTemplate<String, AvroVehicleEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendVehicleEventToKafka(VehicleEvent event) {
        AvroVehicleEvent avroEvent = serialiseVehicleEvent(event);
        kafkaTemplate.send(VEHICLE_TOPIC, avroEvent);
        System.out.println("Event sent to Kafka topic");
    }

    private AvroVehicleEvent serialiseVehicleEvent(VehicleEvent event) {
        AvroVehicleEvent avroEvent = AvroVehicleEvent.newBuilder()
                .setTripId(event.getTripId())
                .setStartTime(event.getStartTime())
                .setStartDate(event.getStartDate())
                .setScheduleRelationship(event.getScheduleRelationship())
                .setRouteId(event.getRouteId())
                .setVehicleTimestamp(event.getVehicleTimestamp())
                .build();

        System.out.println("Serialised");
        //Returns a SpecificRecord
        return avroEvent;
    }

}
