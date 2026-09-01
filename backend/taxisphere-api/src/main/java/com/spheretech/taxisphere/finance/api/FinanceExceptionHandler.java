package com.spheretech.taxisphere.finance.api;

import com.spheretech.taxisphere.finance.application.AssociationProfileRequiredException;
import com.spheretech.taxisphere.finance.application.FinancialTransactionNotFoundException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FinanceExceptionHandler {

    @ExceptionHandler(FinancialTransactionNotFoundException.class)
    public ProblemDetail handleFinancialTransactionNotFound(FinancialTransactionNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/financial-transaction-not-found"));
        detail.setTitle("Financial transaction not found");
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