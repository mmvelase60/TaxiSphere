package com.spheretech.taxisphere.reporting.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record DailyOperationsReportResponse(
        LocalDate businessDate,
        Instant generatedAt,
        long dispatchedTrips,
        long departedTrips,
        long arrivedTrips,
        long cancelledTrips,
        long totalTrips,
        long totalPassengers,
        BigDecimal totalRevenue
) {
}