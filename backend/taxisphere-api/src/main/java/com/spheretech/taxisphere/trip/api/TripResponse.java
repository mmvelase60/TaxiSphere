package com.spheretech.taxisphere.trip.api;

import com.spheretech.taxisphere.trip.domain.Trip;
import com.spheretech.taxisphere.trip.domain.TripStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TripResponse(
        UUID id,
        UUID tenantId,
        UUID associationId,
        UUID vehicleAssignmentId,
        UUID driverId,
        UUID vehicleId,
        UUID routeId,
        int passengerCount,
        BigDecimal farePerPassenger,
        BigDecimal totalRevenue,
        TripStatus status,
        Instant dispatchedAt,
        Instant departedAt,
        Instant arrivedAt,
        Instant createdAt,
        Instant updatedAt
) {
    public static TripResponse from(Trip trip) {
        return new TripResponse(
                trip.getId(),
                trip.getTenantId(),
                trip.getAssociationId(),
                trip.getVehicleAssignmentId(),
                trip.getDriverId(),
                trip.getVehicleId(),
                trip.getRouteId(),
                trip.getPassengerCount(),
                trip.getFarePerPassenger(),
                trip.getTotalRevenue(),
                trip.getStatus(),
                trip.getDispatchedAt(),
                trip.getDepartedAt(),
                trip.getArrivedAt(),
                trip.getCreatedAt(),
                trip.getUpdatedAt()
        );
    }
}
