package com.spheretech.taxisphere.assignment.api;

import com.spheretech.taxisphere.assignment.domain.VehicleAssignment;
import com.spheretech.taxisphere.assignment.domain.VehicleAssignmentStatus;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record VehicleAssignmentResponse(
        UUID id,
        UUID tenantId,
        UUID associationId,
        UUID driverId,
        UUID vehicleId,
        LocalDate assignedDate,
        LocalDate endedDate,
        VehicleAssignmentStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static VehicleAssignmentResponse from(VehicleAssignment assignment) {
        return new VehicleAssignmentResponse(
                assignment.getId(),
                assignment.getTenantId(),
                assignment.getAssociationId(),
                assignment.getDriverId(),
                assignment.getVehicleId(),
                assignment.getAssignedDate(),
                assignment.getEndedDate(),
                assignment.getStatus(),
                assignment.getCreatedAt(),
                assignment.getUpdatedAt()
        );
    }
}
