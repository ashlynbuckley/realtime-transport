package com.fyp.springapp.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * External DTO representation of Json body data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleDTO {
    private Header header;
    private List<Entity> entity;

    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
    }
    public List<Entity> getEntity() {
        return entity;
    }
    public void setEntity(List<Entity> entity) {
        this.entity = entity;
    }
}
