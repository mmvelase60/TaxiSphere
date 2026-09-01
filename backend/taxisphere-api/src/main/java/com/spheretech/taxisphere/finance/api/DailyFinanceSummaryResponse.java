package com.spheretech.taxisphere.finance.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record DailyFinanceSummaryResponse(
        LocalDate businessDate,
        Instant generatedAt,
        BigDecimal totalIncome,
        BigDecimal totalExpenses,
        BigDecimal netAmount
) {
}