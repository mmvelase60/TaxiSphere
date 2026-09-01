package com.spheretech.taxisphere.passenger.application;

public class PassengerEmailAlreadyExistsException extends RuntimeException {

    public PassengerEmailAlreadyExistsException(String email) {
        super("Passenger email already exists for this tenant: " + email);
    }
}