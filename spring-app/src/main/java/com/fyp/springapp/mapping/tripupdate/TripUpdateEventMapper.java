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
        List<StopTimeUpdatePOJO> pojoUpdates = new ArrayList<>();
        //Make list of the stop_time_updates
        for (StopTimeUpdate update : entity.getTripUpdate().getStopTimeUpdate()) {
            Integer departureDelay = update.getDeparture() != null
                    ? update.getDeparture().getDepartureDelay()
                    : null;
            Integer arrivalDelay = update.getArrival() != null
                    ? update.getArrival().getArrivalDelay()
                    : null;
            StopTimeUpdatePOJO pojoUpdate = new StopTimeUpdatePOJO(
                    update.getStopSequence(),
                    departureDelay,
                    arrivalDelay,
                    update.getStopId(),
                    update.getScheduleRelationship()
            );
            pojoUpdates.add(pojoUpdate);
        }

        return new TripUpdateEvent(
                entity.getTripUpdate().getTrip().getTripId(),
                entity.getTripUpdate().getTrip().getStartTime(),
                entity.getTripUpdate().getTrip().getStartDate(),
                entity.getTripUpdate().getTrip().getScheduleRelationship(),
                entity.getTripUpdate().getTrip().getRouteId(),
                entity.getTripUpdate().getTripUpdateTimestamp(),
                pojoUpdates
        );
    }
}
