package com.spheretech.taxisphere.maintenance.api;

import com.spheretech.taxisphere.maintenance.domain.MaintenanceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateMaintenanceRecordRequest(
        @NotNull UUID vehicleId,
        @NotNull MaintenanceType type,
        @NotNull LocalDate scheduledDate,
        @NotNull @DecimalMin("0.00") BigDecimal estimatedCost,
        @Size(max = 160) String serviceProvider,
        @NotBlank @Size(max = 500) String description
) {
}