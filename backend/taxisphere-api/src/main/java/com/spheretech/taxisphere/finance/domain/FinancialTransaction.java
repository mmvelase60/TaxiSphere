package com.spheretech.taxisphere.finance.domain;

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
@Table(name = "financial_transaction")
public class FinancialTransaction {

    @Id
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "association_id", nullable = false)
    private UUID associationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private FinanceTransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 60)
    private FinanceTransactionCategory category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "business_date", nullable = false)
    private LocalDate businessDate;

    @Column(length = 255)
    private String description;

    @Column(name = "reference_type", length = 80)
    private String referenceType;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "reference_id")
    private UUID referenceId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected FinancialTransaction() {
    }

    public FinancialTransaction(
            UUID id,
            UUID tenantId,
            UUID associationId,
            FinanceTransactionType type,
            FinanceTransactionCategory category,
            BigDecimal amount,
            LocalDate businessDate,
            String description,
            String referenceType,
            UUID referenceId
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.associationId = associationId;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.businessDate = businessDate;
        this.description = description;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
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

    public FinanceTransactionType getType() {
        return type;
    }

    public FinanceTransactionCategory getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getBusinessDate() {
        return businessDate;
    }

    public String getDescription() {
        return description;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}