package com.spheretech.taxisphere.route.application;

import java.util.UUID;

public class TaxiRouteNotFoundException extends RuntimeException {

    public TaxiRouteNotFoundException(UUID routeId) {
        super("Taxi route was not found for this tenant: " + routeId);
    }
}
