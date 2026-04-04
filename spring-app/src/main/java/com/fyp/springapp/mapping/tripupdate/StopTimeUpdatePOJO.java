package com.fyp.springapp.mapping.tripupdate;

/**
 * Internal representation of a stop time update event
 */
public class StopTimeUpdatePOJO {
    private String stopSequence;
    private Integer aDelay;
    private Integer dDelay;
    private String stopId;
    private String stopTimeUpdateScheduleRelationship;

    public StopTimeUpdatePOJO(String stopSequence, Integer aDelay, Integer dDelay, String stopId, String stopTimeUpdateScheduleRelationship) {
        this.stopSequence = stopSequence;
        this.aDelay = aDelay;
        this.dDelay = dDelay;
        this.stopId = stopId;
        this.stopTimeUpdateScheduleRelationship = stopTimeUpdateScheduleRelationship;
    }

    public String getStopSequence() {
        return stopSequence;
    }
    public void setStopSequence(String stopSequence) {
        this.stopSequence = stopSequence;
    }
    public Integer getaDelay() {
        return aDelay;
    }
    public void setaDelay(Integer aDelay) {
        this.aDelay = aDelay;
    }
    public Integer getdDelay() {
        return dDelay;
    }
    public void setdDelay(Integer dDelay) {
        this.dDelay = dDelay;
    }
    public String getStopId() {
        return stopId;
    }
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }
    public String getStopTimeUpdateScheduleRelationship() {
        return stopTimeUpdateScheduleRelationship;
    }
    public void setStopTimeUpdateScheduleRelationship(String stopTimeUpdateScheduleRelationship) {
        this.stopTimeUpdateScheduleRelationship = stopTimeUpdateScheduleRelationship;
    }
}
