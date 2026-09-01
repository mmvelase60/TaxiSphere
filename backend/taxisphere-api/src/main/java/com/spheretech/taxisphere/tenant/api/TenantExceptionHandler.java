package com.spheretech.taxisphere.tenant.api;

import com.spheretech.taxisphere.tenant.application.TenantAlreadyExistsException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TenantExceptionHandler {

    @ExceptionHandler(TenantAlreadyExistsException.class)
    public ProblemDetail handleTenantAlreadyExists(TenantAlreadyExistsException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/tenant-already-exists"));
        detail.setTitle("Tenant already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }
}
