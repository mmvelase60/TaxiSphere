package com.spheretech.taxisphere.trip.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record DispatchTripRequest(
        @NotNull UUID vehicleAssignmentId,
        @NotNull UUID routeId,
        @Min(1) int passengerCount
) {
}
