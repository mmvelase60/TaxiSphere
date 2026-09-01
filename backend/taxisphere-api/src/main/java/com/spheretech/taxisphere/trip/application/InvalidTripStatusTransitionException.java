package com.spheretech.taxisphere.trip.application;

import com.spheretech.taxisphere.trip.domain.TripStatus;

public class InvalidTripStatusTransitionException extends RuntimeException {

    public InvalidTripStatusTransitionException(TripStatus currentStatus, TripStatus requestedStatus) {
        super("Cannot transition trip from " + currentStatus + " to " + requestedStatus + ".");
    }
}
