package com.transport.flink.process;

import java.util.ArrayList;

/**
 * (DTO) Represents each individual route that exists in area. Route can have several individual vehicles that fulfil the route
 */
public class Route {
    private final String routeId;
    private final String operator;

    public Route(String routeId, String operator) {
        this.routeId = routeId;
        this.operator = operator;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getOperator() {
        return operator;
    }
}
