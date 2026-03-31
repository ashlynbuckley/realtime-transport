package com.fyp.springapp.mapping.tripupdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fyp.springapp.mapping.Header;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripUpdateDTO {
    private Header header;
    @JsonProperty("entity")
    private List<TripUpdateEntity> entities;

    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
    }
    public List<TripUpdateEntity> getEntities() {
        return entities;
    }
    public void setEntities(List<TripUpdateEntity> entities) {
        this.entities = entities;
    }
}
