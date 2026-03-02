package com.fyp.springapp.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleEventMapper {
    private final ObjectMapper objectMapper;

    public VehicleEventMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //Called by PollingService
    public List<VehicleEvent> mapJsonBodyToPojo(String jsonBody) throws JsonProcessingException {
        VehicleDTO vehicleDTO = objectMapper.readValue(jsonBody, VehicleDTO.class);
        //DTO -> POJO
        return mapDtoToPojo(vehicleDTO);
    }

    private List<VehicleEvent> mapDtoToPojo(VehicleDTO vehicleDTO) {
        List<VehicleEvent> events = new ArrayList<>();
        //Mapping per Entity
        for (Entity entity : vehicleDTO.getEntity()) {
            VehicleEvent vehicleEvent = mapEntityToEvent(entity);
            events.add(vehicleEvent);
        }
        return events;
    }

    private VehicleEvent mapEntityToEvent(Entity entity) {
        //Using getters and setters to move the data over - doing this to separate the internal and external representations of my VehicleEvents
        return new VehicleEvent(
                entity.getVehicle().getTrip().getTripId(),
                entity.getVehicle().getTrip().getStartTime(),
                entity.getVehicle().getTrip().getStartDate(),
                entity.getVehicle().getTrip().getScheduleRelationship(),
                entity.getVehicle().getTrip().getRouteId(),
                entity.getVehicle().getTimestamp()
        );
    }
}
