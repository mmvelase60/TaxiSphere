package com.spheretech.taxisphere.maintenance.api;

import com.spheretech.taxisphere.maintenance.domain.MaintenanceRecord;
import com.spheretech.taxisphere.maintenance.domain.MaintenanceStatus;
import com.spheretech.taxisphere.maintenance.domain.MaintenanceType;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record MaintenanceRecordResponse(
        UUID id,
        UUID tenantId,
        UUID associationId,
        UUID vehicleId,
        MaintenanceType type,
        MaintenanceStatus status,
        LocalDate scheduledDate,
        LocalDate completedDate,
        BigDecimal cost,
        String serviceProvider,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
    public static MaintenanceRecordResponse from(MaintenanceRecord record) {
        return new MaintenanceRecordResponse(
                record.getId(),
                record.getTenantId(),
                record.getAssociationId(),
                record.getVehicleId(),
                record.getType(),
                record.getStatus(),
                record.getScheduledDate(),
                record.getCompletedDate(),
                record.getCost(),
                record.getServiceProvider(),
                record.getDescription(),
                record.getCreatedAt(),
                record.getUpdatedAt()
        );
    }
}