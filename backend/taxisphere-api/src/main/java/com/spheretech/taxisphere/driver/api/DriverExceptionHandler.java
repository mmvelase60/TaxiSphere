package com.spheretech.taxisphere.driver.api;

import com.spheretech.taxisphere.driver.application.AssociationProfileRequiredException;
import com.spheretech.taxisphere.driver.application.DriverLicenseAlreadyExistsException;
import com.spheretech.taxisphere.driver.application.DriverNotFoundException;
import com.spheretech.taxisphere.driver.application.DriverPdpAlreadyExistsException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DriverExceptionHandler {

    @ExceptionHandler(DriverLicenseAlreadyExistsException.class)
    public ProblemDetail handleLicenseAlreadyExists(DriverLicenseAlreadyExistsException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/driver-license-already-exists"));
        detail.setTitle("Driver license already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(DriverPdpAlreadyExistsException.class)
    public ProblemDetail handlePdpAlreadyExists(DriverPdpAlreadyExistsException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/driver-pdp-already-exists"));
        detail.setTitle("Driver PDP already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(DriverNotFoundException.class)
    public ProblemDetail handleDriverNotFound(DriverNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/driver-not-found"));
        detail.setTitle("Driver not found");
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
