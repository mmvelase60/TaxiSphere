package com.spheretech.taxisphere.dashboard.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record DashboardOverviewResponse(
        LocalDate businessDate,
        Instant generatedAt,
        long todayTrips,
        long activeTrips,
        BigDecimal todayRevenue,
        long totalDrivers,
        long availableDrivers,
        long totalVehicles,
        long availableVehicles,
        long totalRanks,
        long totalRoutes
) {
}
