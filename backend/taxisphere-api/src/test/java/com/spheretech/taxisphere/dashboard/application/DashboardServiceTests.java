package com.spheretech.taxisphere.dashboard.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.dashboard.api.DashboardOverviewResponse;
import com.spheretech.taxisphere.driver.domain.DriverStatus;
import com.spheretech.taxisphere.driver.persistence.DriverRepository;
import com.spheretech.taxisphere.rank.persistence.TaxiRankRepository;
import com.spheretech.taxisphere.route.persistence.TaxiRouteRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.trip.domain.TripStatus;
import com.spheretech.taxisphere.trip.persistence.TripRepository;
import com.spheretech.taxisphere.vehicle.domain.VehicleStatus;
import com.spheretech.taxisphere.vehicle.persistence.VehicleRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

class DashboardServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void returnsTenantOverviewMetrics() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        TripRepository trips = Mockito.mock(TripRepository.class);
        DriverRepository drivers = Mockito.mock(DriverRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        TaxiRankRepository ranks = Mockito.mock(TaxiRankRepository.class);
        TaxiRouteRepository routes = Mockito.mock(TaxiRouteRepository.class);

        when(trips.countByTenantIdAndDispatchedAtGreaterThanEqualAndDispatchedAtLessThan(
                Mockito.eq(tenantId),
                ArgumentMatchers.any(Instant.class),
                ArgumentMatchers.any(Instant.class)
        )).thenReturn(12L);
        when(trips.countByTenantIdAndStatusIn(
                tenantId,
                List.of(TripStatus.DISPATCHED, TripStatus.DEPARTED)
        )).thenReturn(4L);
        when(trips.sumRevenueForPeriod(
                Mockito.eq(tenantId),
                ArgumentMatchers.any(Instant.class),
                ArgumentMatchers.any(Instant.class)
        )).thenReturn(new BigDecimal("10200.00"));
        when(drivers.countByTenantId(tenantId)).thenReturn(35L);
        when(drivers.countByTenantIdAndStatus(tenantId, DriverStatus.AVAILABLE)).thenReturn(18L);
        when(vehicles.countByTenantId(tenantId)).thenReturn(28L);
        when(vehicles.countByTenantIdAndStatus(tenantId, VehicleStatus.AVAILABLE)).thenReturn(16L);
        when(ranks.countByTenantId(tenantId)).thenReturn(5L);
        when(routes.countByTenantId(tenantId)).thenReturn(11L);

        DashboardService service = new DashboardService(trips, drivers, vehicles, ranks, routes);
        DashboardOverviewResponse response = service.overview();

        assertThat(response.todayTrips()).isEqualTo(12L);
        assertThat(response.activeTrips()).isEqualTo(4L);
        assertThat(response.todayRevenue()).isEqualByComparingTo("10200.00");
        assertThat(response.totalDrivers()).isEqualTo(35L);
        assertThat(response.availableDrivers()).isEqualTo(18L);
        assertThat(response.totalVehicles()).isEqualTo(28L);
        assertThat(response.availableVehicles()).isEqualTo(16L);
        assertThat(response.totalRanks()).isEqualTo(5L);
        assertThat(response.totalRoutes()).isEqualTo(11L);
    }
}
