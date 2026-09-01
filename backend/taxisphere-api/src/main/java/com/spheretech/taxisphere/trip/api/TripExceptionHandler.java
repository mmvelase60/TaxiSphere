package com.spheretech.taxisphere.trip.api;

import com.spheretech.taxisphere.trip.application.TripNotFoundException;
import com.spheretech.taxisphere.trip.application.VehicleAssignmentNotActiveException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TripExceptionHandler {

    @ExceptionHandler(TripNotFoundException.class)
    public ProblemDetail handleTripNotFound(TripNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/trip-not-found"));
        detail.setTitle("Trip not found");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(VehicleAssignmentNotActiveException.class)
    public ProblemDetail handleVehicleAssignmentNotActive(VehicleAssignmentNotActiveException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/vehicle-assignment-not-active"));
        detail.setTitle("Vehicle assignment not active");
        detail.setDetail(exception.getMessage());
        return detail;
    }
}
