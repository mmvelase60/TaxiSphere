package com.spheretech.taxisphere.maintenance.api;

import com.spheretech.taxisphere.maintenance.application.MaintenanceRecordNotFoundException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MaintenanceExceptionHandler {

    @ExceptionHandler(MaintenanceRecordNotFoundException.class)
    public ProblemDetail handleMaintenanceRecordNotFound(MaintenanceRecordNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/maintenance-record-not-found"));
        detail.setTitle("Maintenance record not found");
        detail.setDetail(exception.getMessage());
        return detail;
    }
}