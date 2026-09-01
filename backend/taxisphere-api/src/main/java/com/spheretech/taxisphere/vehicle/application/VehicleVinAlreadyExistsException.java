package com.spheretech.taxisphere.vehicle.application;

public class VehicleVinAlreadyExistsException extends RuntimeException {

    public VehicleVinAlreadyExistsException(String vin) {
        super("Vehicle VIN already exists for this tenant: " + vin);
    }
}
