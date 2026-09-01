package com.spheretech.taxisphere.tenant.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTenantRequest(
        @NotBlank @Size(max = 160) String name,
        @NotBlank @Email @Size(max = 180) String contactEmail
) {
}
