package com.spheretech.taxisphere.association.api;

import com.spheretech.taxisphere.association.application.AssociationAlreadyExistsException;
import com.spheretech.taxisphere.association.application.AssociationNotFoundException;
import com.spheretech.taxisphere.association.application.AssociationRegistrationAlreadyExistsException;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AssociationExceptionHandler {

    @ExceptionHandler(AssociationAlreadyExistsException.class)
    public ProblemDetail handleAssociationAlreadyExists(AssociationAlreadyExistsException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/association-already-exists"));
        detail.setTitle("Association already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(AssociationRegistrationAlreadyExistsException.class)
    public ProblemDetail handleAssociationRegistrationAlreadyExists(
            AssociationRegistrationAlreadyExistsException exception
    ) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setType(URI.create("https://api.taxisphere.local/problems/association-registration-already-exists"));
        detail.setTitle("Association registration already exists");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(AssociationNotFoundException.class)
    public ProblemDetail handleAssociationNotFound(AssociationNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setType(URI.create("https://api.taxisphere.local/problems/association-not-found"));
        detail.setTitle("Association not found");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(TenantContextRequiredException.class)
    public ProblemDetail handleTenantContextRequired(TenantContextRequiredException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        detail.setType(URI.create("https://api.taxisphere.local/problems/tenant-context-required"));
        detail.setTitle("Tenant context required");
        detail.setDetail(exception.getMessage());
        return detail;
    }
}
