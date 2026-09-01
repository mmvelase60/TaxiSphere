package com.spheretech.taxisphere.vehicle.domain;

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
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "association_id", nullable = false)
    private UUID associationId;

    @Column(name = "registration_number", nullable = false, length = 40)
    private String registrationNumber;

    @Column(nullable = false, length = 80)
    private String make;

    @Column(nullable = false, length = 80)
    private String model;

    @Column(name = "model_year", nullable = false)
    private int modelYear;

    @Column(name = "seating_capacity", nullable = false)
    private int seatingCapacity;

    @Column(length = 80)
    private String vin;

    @Column(name = "roadworthy_expiry_date", nullable = false)
    private LocalDate roadworthyExpiryDate;

    @Column(name = "insurance_expiry_date", nullable = false)
    private LocalDate insuranceExpiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private VehicleStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Vehicle() {
    }

    public Vehicle(
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
            VehicleStatus status
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.associationId = associationId;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.modelYear = modelYear;
        this.seatingCapacity = seatingCapacity;
        this.vin = vin;
        this.roadworthyExpiryDate = roadworthyExpiryDate;
        this.insuranceExpiryDate = insuranceExpiryDate;
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getModelYear() {
        return modelYear;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public String getVin() {
        return vin;
    }

    public LocalDate getRoadworthyExpiryDate() {
        return roadworthyExpiryDate;
    }

    public LocalDate getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
