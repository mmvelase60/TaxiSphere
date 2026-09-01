package com.spheretech.taxisphere.passenger.application;

public class PassengerPhoneAlreadyExistsException extends RuntimeException {

    public PassengerPhoneAlreadyExistsException(String phoneNumber) {
        super("Passenger phone number already exists for this tenant: " + phoneNumber);
    }
}