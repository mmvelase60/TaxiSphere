package com.spheretech.taxisphere.maintenance.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "maintenance_record")
public class MaintenanceRecord {

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
    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private MaintenanceType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private MaintenanceStatus status;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal cost;

    @Column(name = "service_provider", length = 160)
    private String serviceProvider;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected MaintenanceRecord() {
    }

    public MaintenanceRecord(
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
            String description
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.associationId = associationId;
        this.vehicleId = vehicleId;
        this.type = type;
        this.status = status;
        this.scheduledDate = scheduledDate;
        this.completedDate = completedDate;
        this.cost = cost;
        this.serviceProvider = serviceProvider;
        this.description = description;
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

    public void markInProgress() {
        status = MaintenanceStatus.IN_PROGRESS;
    }

    public void markCompleted(LocalDate completedDate, BigDecimal finalCost) {
        status = MaintenanceStatus.COMPLETED;
        this.completedDate = completedDate;
        cost = finalCost;
    }

    public void cancel() {
        status = MaintenanceStatus.CANCELLED;
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

    public UUID getVehicleId() {
        return vehicleId;
    }

    public MaintenanceType getType() {
        return type;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}