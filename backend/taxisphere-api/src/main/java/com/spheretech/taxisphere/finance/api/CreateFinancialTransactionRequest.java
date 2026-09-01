package com.spheretech.taxisphere.finance.api;

import com.spheretech.taxisphere.finance.domain.FinanceTransactionCategory;
import com.spheretech.taxisphere.finance.domain.FinanceTransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateFinancialTransactionRequest(
        @NotNull FinanceTransactionType type,
        @NotNull FinanceTransactionCategory category,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotNull LocalDate businessDate,
        @Size(max = 255) String description,
        @Size(max = 80) String referenceType,
        UUID referenceId
) {
}