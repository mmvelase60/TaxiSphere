package com.spheretech.taxisphere.trip.application;

import java.util.UUID;

public class TripNotFoundException extends RuntimeException {

    public TripNotFoundException(UUID tripId) {
        super("Trip was not found for this tenant: " + tripId);
    }
}
