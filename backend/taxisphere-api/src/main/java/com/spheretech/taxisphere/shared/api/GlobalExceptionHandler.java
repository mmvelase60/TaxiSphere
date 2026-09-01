package com.spheretech.taxisphere.shared.api;

import com.spheretech.taxisphere.identity.service.InvalidCredentialsException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setType(URI.create("https://api.taxisphere.local/problems/validation-error"));
        detail.setTitle("Validation failed");
        detail.setDetail("One or more fields are invalid.");
        detail.setProperty("errorCount", exception.getBindingResult().getErrorCount());
        return detail;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentials() {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        detail.setType(URI.create("https://api.taxisphere.local/problems/invalid-credentials"));
        detail.setTitle("Invalid credentials");
        detail.setDetail("The username or password is incorrect.");
        return detail;
    }
}
