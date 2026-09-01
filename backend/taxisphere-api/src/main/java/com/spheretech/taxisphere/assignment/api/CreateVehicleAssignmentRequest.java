package com.spheretech.taxisphere.assignment.api;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record CreateVehicleAssignmentRequest(
        @NotNull UUID driverId,
        @NotNull UUID vehicleId,
        @NotNull LocalDate assignedDate
) {
}
