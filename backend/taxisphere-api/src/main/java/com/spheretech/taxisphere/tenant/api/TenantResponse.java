package com.spheretech.taxisphere.tenant.api;

import com.spheretech.taxisphere.tenant.domain.Tenant;
import com.spheretech.taxisphere.tenant.domain.TenantStatus;
import java.time.Instant;
import java.util.UUID;

public record TenantResponse(
        UUID id,
        String name,
        String contactEmail,
        TenantStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static TenantResponse from(Tenant tenant) {
        return new TenantResponse(
                tenant.getId(),
                tenant.getName(),
                tenant.getContactEmail(),
                tenant.getStatus(),
                tenant.getCreatedAt(),
                tenant.getUpdatedAt()
        );
    }
}
