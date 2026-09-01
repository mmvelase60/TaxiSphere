package com.spheretech.taxisphere.rank.api;

import com.spheretech.taxisphere.rank.application.AssociationProfileRequiredException;
import com.spheretech.taxisphere.rank.application.TaxiRankCodeAlreadyExistsException;
import com.spheretech.taxisphere.rank.application.TaxiRankNotFoundException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TaxiRankExceptionHandler {

    @ExceptionHandler(TaxiRankCodeAlreadyExistsException.class)
    public ProblemDetail handleRankCodeAlreadyExists(TaxiRankCodeAlreadyExistsException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/rank-code-already-exists"));
        detail.setTitle("Taxi rank code already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(TaxiRankNotFoundException.class)
    public ProblemDetail handleRankNotFound(TaxiRankNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/rank-not-found"));
        detail.setTitle("Taxi rank not found");
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
