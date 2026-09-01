package com.spheretech.taxisphere.maintenance.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record MaintenanceSummaryResponse(
        LocalDate startDate,
        LocalDate endDate,
        Instant generatedAt,
        long scheduled,
        long inProgress,
        long completed,
        long cancelled,
        long dueInRange,
        BigDecimal estimatedCostInRange
) {
}