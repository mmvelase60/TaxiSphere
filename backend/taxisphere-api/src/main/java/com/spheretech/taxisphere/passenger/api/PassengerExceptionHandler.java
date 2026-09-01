package com.spheretech.taxisphere.passenger.api;

import com.spheretech.taxisphere.passenger.application.AssociationProfileRequiredException;
import com.spheretech.taxisphere.passenger.application.PassengerEmailAlreadyExistsException;
import com.spheretech.taxisphere.passenger.application.PassengerNotFoundException;
import com.spheretech.taxisphere.passenger.application.PassengerPhoneAlreadyExistsException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PassengerExceptionHandler {

    @ExceptionHandler(PassengerPhoneAlreadyExistsException.class)
    public ProblemDetail handlePhoneAlreadyExists(PassengerPhoneAlreadyExistsException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/passenger-phone-already-exists"));
        detail.setTitle("Passenger phone already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(PassengerEmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExists(PassengerEmailAlreadyExistsException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/passenger-email-already-exists"));
        detail.setTitle("Passenger email already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(PassengerNotFoundException.class)
    public ProblemDetail handlePassengerNotFound(PassengerNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/passenger-not-found"));
        detail.setTitle("Passenger not found");
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