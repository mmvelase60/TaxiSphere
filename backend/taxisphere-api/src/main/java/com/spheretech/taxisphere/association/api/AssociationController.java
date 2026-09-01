package com.spheretech.taxisphere.association.api;

import com.spheretech.taxisphere.association.application.AssociationService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/associations")
public class AssociationController {

    private final AssociationService associationService;

    public AssociationController(AssociationService associationService) {
        this.associationService = associationService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ASSOCIATION_ADMIN')")
    public AssociationResponse getCurrentTenantAssociation() {
        return AssociationResponse.from(associationService.getCurrentTenantAssociation());
    }

    @PostMapping
    @PreAuthorize("hasRole('ASSOCIATION_ADMIN')")
    public ResponseEntity<AssociationResponse> create(@Valid @RequestBody CreateAssociationRequest request) {
        AssociationResponse response = AssociationResponse.from(associationService.createAssociation(request));
        return ResponseEntity
                .created(URI.create("/api/v1/associations/" + response.id()))
                .body(response);
    }
}
