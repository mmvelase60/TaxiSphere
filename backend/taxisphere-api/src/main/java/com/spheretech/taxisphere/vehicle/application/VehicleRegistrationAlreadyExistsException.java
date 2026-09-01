package com.spheretech.taxisphere.vehicle.application;

public class VehicleRegistrationAlreadyExistsException extends RuntimeException {

    public VehicleRegistrationAlreadyExistsException(String registrationNumber) {
        super("Vehicle registration number already exists for this tenant: " + registrationNumber);
    }
}
