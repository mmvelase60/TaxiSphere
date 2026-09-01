package com.spheretech.taxisphere.trip.application;

import java.util.UUID;

public class VehicleAssignmentNotActiveException extends RuntimeException {

    public VehicleAssignmentNotActiveException(UUID assignmentId) {
        super("Vehicle assignment is not active for dispatch: " + assignmentId);
    }
}
