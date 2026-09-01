package com.spheretech.taxisphere.route.api;

import com.spheretech.taxisphere.route.application.OriginRankNotFoundException;
import com.spheretech.taxisphere.route.application.TaxiRouteCodeAlreadyExistsException;
import com.spheretech.taxisphere.route.application.TaxiRouteNotFoundException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TaxiRouteExceptionHandler {

    @ExceptionHandler(TaxiRouteCodeAlreadyExistsException.class)
    public ProblemDetail handleRouteCodeAlreadyExists(TaxiRouteCodeAlreadyExistsException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/route-code-already-exists"));
        detail.setTitle("Taxi route code already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(TaxiRouteNotFoundException.class)
    public ProblemDetail handleRouteNotFound(TaxiRouteNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/route-not-found"));
        detail.setTitle("Taxi route not found");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(OriginRankNotFoundException.class)
    public ProblemDetail handleOriginRankNotFound(OriginRankNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/origin-rank-not-found"));
        detail.setTitle("Origin rank not found");
        detail.setDetail(exception.getMessage());
        return detail;
    }
}
