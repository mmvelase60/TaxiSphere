package com.spheretech.taxisphere.rank.api;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateTaxiRankRequest(
        @NotBlank @Size(max = 160) String name,
        @NotBlank @Size(max = 40) String code,
        @NotBlank @Size(max = 255) String address,
        @NotBlank @Size(max = 120) String city,
        @NotBlank @Size(max = 120) String province,
        @Min(1) int capacity,
        @DecimalMin("-90.0") @DecimalMax("90.0") BigDecimal latitude,
        @DecimalMin("-180.0") @DecimalMax("180.0") BigDecimal longitude,
        @Size(max = 160) String operatingHours
) {
}
