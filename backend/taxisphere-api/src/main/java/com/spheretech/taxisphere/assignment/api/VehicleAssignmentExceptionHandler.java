package com.spheretech.taxisphere.assignment.api;

import com.spheretech.taxisphere.assignment.application.DriverAlreadyAssignedException;
import com.spheretech.taxisphere.assignment.application.VehicleAlreadyAssignedException;
import com.spheretech.taxisphere.assignment.application.VehicleAssignmentNotFoundException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VehicleAssignmentExceptionHandler {

    @ExceptionHandler(DriverAlreadyAssignedException.class)
    public ProblemDetail handleDriverAlreadyAssigned(DriverAlreadyAssignedException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/driver-already-assigned"));
        detail.setTitle("Driver already assigned");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(VehicleAlreadyAssignedException.class)
    public ProblemDetail handleVehicleAlreadyAssigned(VehicleAlreadyAssignedException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/vehicle-already-assigned"));
        detail.setTitle("Vehicle already assigned");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(VehicleAssignmentNotFoundException.class)
    public ProblemDetail handleAssignmentNotFound(VehicleAssignmentNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/vehicle-assignment-not-found"));
        detail.setTitle("Vehicle assignment not found");
        detail.setDetail(exception.getMessage());
        return detail;
    }
}
