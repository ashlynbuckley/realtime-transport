package com.fyp.springapp.mapping.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fyp.springapp.mapping.Header;

import java.util.List;

/**
 * External DTO representation of Json body data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleDTO {
    private Header header;
    private List<VehicleEntity> entities;

    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
    }
    public List<VehicleEntity> getEntities() {
        return entities;
    }
    public void setEntities(List<VehicleEntity> entities) {
        this.entities = entities;
    }
}
