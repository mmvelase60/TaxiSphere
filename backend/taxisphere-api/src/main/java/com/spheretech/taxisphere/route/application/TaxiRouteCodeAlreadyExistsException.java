package com.spheretech.taxisphere.route.application;

public class TaxiRouteCodeAlreadyExistsException extends RuntimeException {

    public TaxiRouteCodeAlreadyExistsException(String code) {
        super("Taxi route code already exists for this tenant: " + code);
    }
}
