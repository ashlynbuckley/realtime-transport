package com.fyp.springapp.mapping.tripupdate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripUpdateEventMapper {
    private final ObjectMapper objectMapper;

    public TripUpdateEventMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //Called by PollingService
    public List<TripUpdateEvent> mapJsonBodyToPojo(String jsonBody) throws JsonProcessingException {
        TripUpdateDTO tripUpdateDTO = objectMapper.readValue(jsonBody, TripUpdateDTO.class);
        //DTO -> POJO
        return mapDtoToPojo(tripUpdateDTO);
    }

    private List<TripUpdateEvent> mapDtoToPojo(TripUpdateDTO tripUpdateDTO) {
        List<TripUpdateEvent> events = new ArrayList<>();
        //Mapping per Entity
        for (TripUpdateEntity entity : tripUpdateDTO.getEntities()) {
            TripUpdateEvent tripUpdate = mapEntityToEvent(entity);
            events.add(tripUpdate);
        }
        return events;
    }

    private TripUpdateEvent mapEntityToEvent(TripUpdateEntity entity) {
        //Using getters and setters to move the data over - doing this to separate the internal and external representations of my VehicleEvents
        return new TripUpdateEvent(
                entity.getTripUpdate().getTrip().getTripId(),
                entity.getTripUpdate().getTrip().getStartTime(),
                entity.getTripUpdate().getTrip().getStartDate(),
                entity.getTripUpdate().getTrip().getScheduleRelationship(),
                entity.getTripUpdate().getTrip().getRouteId(),
                entity.getTripUpdate().getTripUpdateTimestamp(),
                //do another loop
                for (StopTimeUpdate stopTimeUpdateItem : entity.getTripUpdate().getStopTimeUpdate()) {
                  StopTimeUpdate stopTimeUpdateEntity = new StopTimeUpdate()
                }
                entity.getTripUpdate().getStopTimeUpdate().getStopSequence(),
        );
    }
}
