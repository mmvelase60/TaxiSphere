package com.spheretech.taxisphere.vehicle.api;

import com.spheretech.taxisphere.vehicle.application.AssociationProfileRequiredException;
import com.spheretech.taxisphere.vehicle.application.VehicleNotFoundException;
import com.spheretech.taxisphere.vehicle.application.VehicleRegistrationAlreadyExistsException;
import com.spheretech.taxisphere.vehicle.application.VehicleVinAlreadyExistsException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VehicleExceptionHandler {

    @ExceptionHandler(VehicleRegistrationAlreadyExistsException.class)
    public ProblemDetail handleRegistrationAlreadyExists(VehicleRegistrationAlreadyExistsException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/vehicle-registration-already-exists"));
        detail.setTitle("Vehicle registration already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(VehicleVinAlreadyExistsException.class)
    public ProblemDetail handleVinAlreadyExists(VehicleVinAlreadyExistsException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/vehicle-vin-already-exists"));
        detail.setTitle("Vehicle VIN already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ProblemDetail handleVehicleNotFound(VehicleNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/vehicle-not-found"));
        detail.setTitle("Vehicle not found");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(AssociationProfileRequiredException.class)
    public ProblemDetail handleAssociationProfileRequired(AssociationProfileRequiredException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/association-profile-required"));
        detail.setTitle("Association profile required");
        detail.setDetail(exception.getMessage());
        return detail;
    }
}
