package com.spheretech.taxisphere.passenger.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreatePassengerRequest(
        UUID userAccountId,
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @NotBlank @Size(max = 40) String phoneNumber,
        @Email @Size(max = 180) String email
) {
}