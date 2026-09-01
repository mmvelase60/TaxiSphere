package com.spheretech.taxisphere.dashboard.application;

import com.spheretech.taxisphere.dashboard.api.DashboardOverviewResponse;
import com.spheretech.taxisphere.driver.domain.DriverStatus;
import com.spheretech.taxisphere.driver.persistence.DriverRepository;
import com.spheretech.taxisphere.rank.persistence.TaxiRankRepository;
import com.spheretech.taxisphere.route.persistence.TaxiRouteRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import com.spheretech.taxisphere.trip.domain.TripStatus;
import com.spheretech.taxisphere.trip.persistence.TripRepository;
import com.spheretech.taxisphere.vehicle.domain.VehicleStatus;
import com.spheretech.taxisphere.vehicle.persistence.VehicleRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final TaxiRankRepository rankRepository;
    private final TaxiRouteRepository routeRepository;

    public DashboardService(
            TripRepository tripRepository,
            DriverRepository driverRepository,
            VehicleRepository vehicleRepository,
            TaxiRankRepository rankRepository,
            TaxiRouteRepository routeRepository
    ) {
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
        this.rankRepository = rankRepository;
        this.routeRepository = routeRepository;
    }

    @Transactional(readOnly = true)
    public DashboardOverviewResponse overview() {
        UUID tenantId = currentTenantId();
        Instant generatedAt = Instant.now();
        LocalDate businessDate = LocalDate.now(ZoneOffset.UTC);
        Instant startOfDay = businessDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant startOfNextDay = businessDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        long todayTrips = tripRepository.countByTenantIdAndDispatchedAtGreaterThanEqualAndDispatchedAtLessThan(
                tenantId,
                startOfDay,
                startOfNextDay
        );
        long activeTrips = tripRepository.countByTenantIdAndStatusIn(
                tenantId,
                List.of(TripStatus.DISPATCHED, TripStatus.DEPARTED)
        );
        BigDecimal todayRevenue = tripRepository.sumRevenueForPeriod(tenantId, startOfDay, startOfNextDay);
        if (todayRevenue == null) {
            todayRevenue = BigDecimal.ZERO;
        }

        return new DashboardOverviewResponse(
                businessDate,
                generatedAt,
                todayTrips,
                activeTrips,
                todayRevenue,
                driverRepository.countByTenantId(tenantId),
                driverRepository.countByTenantIdAndStatus(tenantId, DriverStatus.AVAILABLE),
                vehicleRepository.countByTenantId(tenantId),
                vehicleRepository.countByTenantIdAndStatus(tenantId, VehicleStatus.AVAILABLE),
                rankRepository.countByTenantId(tenantId),
                routeRepository.countByTenantId(tenantId)
        );
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}
