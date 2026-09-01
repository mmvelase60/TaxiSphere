package com.spheretech.taxisphere.passenger.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "passenger")
public class Passenger {

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
    @Column(name = "user_account_id")
    private UUID userAccountId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "phone_number", nullable = false, length = 40)
    private String phoneNumber;

    @Column(length = 180)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private PassengerStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Passenger() {
    }

    public Passenger(
            UUID id,
            UUID tenantId,
            UUID associationId,
            UUID userAccountId,
            String firstName,
            String lastName,
            String phoneNumber,
            String email,
            PassengerStatus status
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.associationId = associationId;
        this.userAccountId = userAccountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    public UUID getUserAccountId() {
        return userAccountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public PassengerStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}