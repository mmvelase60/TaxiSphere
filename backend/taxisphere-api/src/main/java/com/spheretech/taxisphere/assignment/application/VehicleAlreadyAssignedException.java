package com.spheretech.taxisphere.assignment.application;

import java.util.UUID;

public class VehicleAlreadyAssignedException extends RuntimeException {

    public VehicleAlreadyAssignedException(UUID vehicleId) {
        super("Vehicle already has an active driver assignment: " + vehicleId);
    }
}
