package com.spheretech.taxisphere.driver.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreateDriverRequest(
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @NotBlank @Size(max = 40) String phoneNumber,
        @Email @Size(max = 180) String email,
        @NotBlank @Size(max = 80) String licenseNumber,
        @NotBlank @Size(max = 80) String pdpNumber,
        @NotNull @Future LocalDate licenseExpiryDate,
        @NotNull @Future LocalDate pdpExpiryDate
) {
}
