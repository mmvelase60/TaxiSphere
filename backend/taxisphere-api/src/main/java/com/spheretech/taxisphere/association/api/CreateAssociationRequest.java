package com.spheretech.taxisphere.association.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAssociationRequest(
        @NotBlank @Size(max = 160) String name,
        @Size(max = 80) String registrationNumber,
        @NotBlank @Email @Size(max = 180) String contactEmail,
        @Size(max = 40) String contactPhone
) {
}
