package com.spheretech.taxisphere.route.api;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateTaxiRouteRequest(
        UUID originRankId,
        @NotBlank @Size(max = 40) String code,
        @NotBlank @Size(max = 160) String origin,
        @NotBlank @Size(max = 160) String destination,
        @NotNull @DecimalMin("0.00") BigDecimal fare,
        @NotNull @DecimalMin("0.01") BigDecimal distanceKm,
        @Min(1) int estimatedMinutes
) {
}
