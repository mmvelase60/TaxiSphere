package com.spheretech.taxisphere.finance.api;

import com.spheretech.taxisphere.finance.domain.FinanceTransactionCategory;
import com.spheretech.taxisphere.finance.domain.FinanceTransactionType;
import com.spheretech.taxisphere.finance.domain.FinancialTransaction;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record FinancialTransactionResponse(
        UUID id,
        UUID tenantId,
        UUID associationId,
        FinanceTransactionType type,
        FinanceTransactionCategory category,
        BigDecimal amount,
        LocalDate businessDate,
        String description,
        String referenceType,
        UUID referenceId,
        Instant createdAt,
        Instant updatedAt
) {
    public static FinancialTransactionResponse from(FinancialTransaction transaction) {
        return new FinancialTransactionResponse(
                transaction.getId(),
                transaction.getTenantId(),
                transaction.getAssociationId(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getAmount(),
                transaction.getBusinessDate(),
                transaction.getDescription(),
                transaction.getReferenceType(),
                transaction.getReferenceId(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
}