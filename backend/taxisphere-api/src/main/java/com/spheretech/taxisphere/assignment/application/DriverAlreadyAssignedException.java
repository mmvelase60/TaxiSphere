package com.spheretech.taxisphere.assignment.application;

import java.util.UUID;

public class DriverAlreadyAssignedException extends RuntimeException {

    public DriverAlreadyAssignedException(UUID driverId) {
        super("Driver already has an active vehicle assignment: " + driverId);
    }
}
