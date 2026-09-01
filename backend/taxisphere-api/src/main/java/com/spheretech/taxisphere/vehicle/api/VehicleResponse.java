package com.spheretech.taxisphere.vehicle.api;

import com.spheretech.taxisphere.vehicle.domain.Vehicle;
import com.spheretech.taxisphere.vehicle.domain.VehicleStatus;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record VehicleResponse(
        UUID id,
        UUID tenantId,
        UUID associationId,
        String registrationNumber,
        String make,
        String model,
        int modelYear,
        int seatingCapacity,
        String vin,
        LocalDate roadworthyExpiryDate,
        LocalDate insuranceExpiryDate,
        VehicleStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static VehicleResponse from(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getTenantId(),
                vehicle.getAssociationId(),
                vehicle.getRegistrationNumber(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getModelYear(),
                vehicle.getSeatingCapacity(),
                vehicle.getVin(),
                vehicle.getRoadworthyExpiryDate(),
                vehicle.getInsuranceExpiryDate(),
                vehicle.getStatus(),
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }
}
