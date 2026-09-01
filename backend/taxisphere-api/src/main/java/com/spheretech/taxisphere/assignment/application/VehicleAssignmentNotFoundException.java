package com.spheretech.taxisphere.assignment.application;

import java.util.UUID;

public class VehicleAssignmentNotFoundException extends RuntimeException {

    public VehicleAssignmentNotFoundException(UUID assignmentId) {
        super("Vehicle assignment was not found for this tenant: " + assignmentId);
    }
}
