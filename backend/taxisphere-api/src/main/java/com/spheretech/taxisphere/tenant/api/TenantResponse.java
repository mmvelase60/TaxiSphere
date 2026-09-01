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
        UUID adminUserId,
        Instant createdAt,
        Instant updatedAt
) {
    public static TenantResponse from(Tenant tenant) {
        return from(tenant, null);
    }

    public static TenantResponse from(Tenant tenant, UUID adminUserId) {
        return new TenantResponse(
                tenant.getId(),
                tenant.getName(),
                tenant.getContactEmail(),
                tenant.getStatus(),
                adminUserId,
                tenant.getCreatedAt(),
                tenant.getUpdatedAt()
        );
    }
}
