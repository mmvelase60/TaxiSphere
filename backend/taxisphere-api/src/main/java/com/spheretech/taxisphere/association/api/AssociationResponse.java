package com.spheretech.taxisphere.association.api;

import com.spheretech.taxisphere.association.domain.AssociationStatus;
import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import java.time.Instant;
import java.util.UUID;

public record AssociationResponse(
        UUID id,
        UUID tenantId,
        String name,
        String registrationNumber,
        String contactEmail,
        String contactPhone,
        AssociationStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static AssociationResponse from(TaxiAssociation association) {
        return new AssociationResponse(
                association.getId(),
                association.getTenantId(),
                association.getName(),
                association.getRegistrationNumber(),
                association.getContactEmail(),
                association.getContactPhone(),
                association.getStatus(),
                association.getCreatedAt(),
                association.getUpdatedAt()
        );
    }
}
