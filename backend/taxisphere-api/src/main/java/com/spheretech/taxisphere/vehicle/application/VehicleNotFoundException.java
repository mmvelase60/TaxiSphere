package com.spheretech.taxisphere.vehicle.application;

import java.util.UUID;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(UUID vehicleId) {
        super("Vehicle was not found for this tenant: " + vehicleId);
    }
}
