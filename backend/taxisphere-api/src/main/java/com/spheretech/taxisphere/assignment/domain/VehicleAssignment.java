package com.spheretech.taxisphere.assignment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "vehicle_assignment")
public class VehicleAssignment {

    @Id
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "association_id", nullable = false)
    private UUID associationId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "driver_id", nullable = false)
    private UUID driverId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @Column(name = "assigned_date", nullable = false)
    private LocalDate assignedDate;

    @Column(name = "ended_date")
    private LocalDate endedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private VehicleAssignmentStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected VehicleAssignment() {
    }

    public VehicleAssignment(
            UUID id,
            UUID tenantId,
            UUID associationId,
            UUID driverId,
            UUID vehicleId,
            LocalDate assignedDate,
            VehicleAssignmentStatus status
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.associationId = associationId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.assignedDate = assignedDate;
        this.status = status;
    }

    @PrePersist
    void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public UUID getAssociationId() {
        return associationId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public LocalDate getEndedDate() {
        return endedDate;
    }

    public VehicleAssignmentStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
