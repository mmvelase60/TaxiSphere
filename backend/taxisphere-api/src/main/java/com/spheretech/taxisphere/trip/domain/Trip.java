package com.spheretech.taxisphere.trip.domain;

import com.spheretech.taxisphere.trip.application.InvalidTripStatusTransitionException;
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
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "trip")
public class Trip {

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
    @Column(name = "vehicle_assignment_id", nullable = false)
    private UUID vehicleAssignmentId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "driver_id", nullable = false)
    private UUID driverId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "route_id", nullable = false)
    private UUID routeId;

    @Column(name = "passenger_count", nullable = false)
    private int passengerCount;

    @Column(name = "fare_per_passenger", nullable = false, precision = 10, scale = 2)
    private BigDecimal farePerPassenger;

    @Column(name = "total_revenue", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalRevenue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TripStatus status;

    @Column(name = "dispatched_at", nullable = false)
    private Instant dispatchedAt;

    @Column(name = "departed_at")
    private Instant departedAt;

    @Column(name = "arrived_at")
    private Instant arrivedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Trip() {
    }

    public Trip(
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
            Instant dispatchedAt
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.associationId = associationId;
        this.vehicleAssignmentId = vehicleAssignmentId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.routeId = routeId;
        this.passengerCount = passengerCount;
        this.farePerPassenger = farePerPassenger;
        this.totalRevenue = totalRevenue;
        this.status = status;
        this.dispatchedAt = dispatchedAt;
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

    public void markDeparted(Instant departedAt) {
        if (status != TripStatus.DISPATCHED) {
            throw new InvalidTripStatusTransitionException(status, TripStatus.DEPARTED);
        }
        this.status = TripStatus.DEPARTED;
        this.departedAt = departedAt;
    }

    public void markArrived(Instant arrivedAt) {
        if (status != TripStatus.DEPARTED) {
            throw new InvalidTripStatusTransitionException(status, TripStatus.ARRIVED);
        }
        this.status = TripStatus.ARRIVED;
        this.arrivedAt = arrivedAt;
    }

    public void cancel() {
        if (status == TripStatus.ARRIVED || status == TripStatus.CANCELLED) {
            throw new InvalidTripStatusTransitionException(status, TripStatus.CANCELLED);
        }
        this.status = TripStatus.CANCELLED;
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

    public UUID getVehicleAssignmentId() {
        return vehicleAssignmentId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public UUID getRouteId() {
        return routeId;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public BigDecimal getFarePerPassenger() {
        return farePerPassenger;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public TripStatus getStatus() {
        return status;
    }

    public Instant getDispatchedAt() {
        return dispatchedAt;
    }

    public Instant getDepartedAt() {
        return departedAt;
    }

    public Instant getArrivedAt() {
        return arrivedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
