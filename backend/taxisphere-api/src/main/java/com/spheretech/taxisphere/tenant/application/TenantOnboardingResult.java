package com.spheretech.taxisphere.tenant.application;

import com.spheretech.taxisphere.tenant.domain.Tenant;
import java.util.UUID;

public record TenantOnboardingResult(
        Tenant tenant,
        UUID adminUserId
) {
}
