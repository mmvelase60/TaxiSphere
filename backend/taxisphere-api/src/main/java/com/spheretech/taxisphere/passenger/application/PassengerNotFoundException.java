package com.spheretech.taxisphere.passenger.application;

import java.util.UUID;

public class PassengerNotFoundException extends RuntimeException {

    public PassengerNotFoundException(UUID passengerId) {
        super("Passenger was not found for this tenant: " + passengerId);
    }
}