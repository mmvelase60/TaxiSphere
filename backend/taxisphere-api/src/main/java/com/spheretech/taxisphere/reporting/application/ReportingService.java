package com.spheretech.taxisphere.reporting.application;

import com.spheretech.taxisphere.reporting.api.DailyOperationsReportResponse;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import com.spheretech.taxisphere.trip.domain.TripStatus;
import com.spheretech.taxisphere.trip.persistence.TripRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportingService {

    private final TripRepository tripRepository;

    public ReportingService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Transactional(readOnly = true)
    public DailyOperationsReportResponse dailyOperations(LocalDate requestedDate) {
        UUID tenantId = currentTenantId();
        LocalDate businessDate = requestedDate == null ? LocalDate.now(ZoneOffset.UTC) : requestedDate;
        Instant startOfDay = businessDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant startOfNextDay = businessDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        long dispatchedTrips = countByStatus(tenantId, TripStatus.DISPATCHED, startOfDay, startOfNextDay);
        long departedTrips = countByStatus(tenantId, TripStatus.DEPARTED, startOfDay, startOfNextDay);
        long arrivedTrips = countByStatus(tenantId, TripStatus.ARRIVED, startOfDay, startOfNextDay);
        long cancelledTrips = countByStatus(tenantId, TripStatus.CANCELLED, startOfDay, startOfNextDay);
        long totalTrips = dispatchedTrips + departedTrips + arrivedTrips + cancelledTrips;
        Long totalPassengers = tripRepository.sumPassengerCountForPeriod(tenantId, startOfDay, startOfNextDay);
        BigDecimal totalRevenue = tripRepository.sumRevenueForPeriod(tenantId, startOfDay, startOfNextDay);

        return new DailyOperationsReportResponse(
                businessDate,
                Instant.now(),
                dispatchedTrips,
                departedTrips,
                arrivedTrips,
                cancelledTrips,
                totalTrips,
                totalPassengers == null ? 0L : totalPassengers,
                totalRevenue == null ? BigDecimal.ZERO : totalRevenue
        );
    }

    private long countByStatus(UUID tenantId, TripStatus status, Instant start, Instant end) {
        return tripRepository.countByTenantIdAndStatusAndDispatchedAtGreaterThanEqualAndDispatchedAtLessThan(
                tenantId,
                status,
                start,
                end
        );
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}