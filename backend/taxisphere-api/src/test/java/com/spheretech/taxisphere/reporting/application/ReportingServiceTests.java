package com.spheretech.taxisphere.reporting.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.reporting.api.DailyOperationsReportResponse;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import com.spheretech.taxisphere.trip.domain.TripStatus;
import com.spheretech.taxisphere.trip.persistence.TripRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

class ReportingServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void returnsDailyOperationsReportForCurrentTenant() {
        UUID tenantId = UUID.randomUUID();
        LocalDate businessDate = LocalDate.of(2026, 9, 1);
        TenantContextHolder.set(new TenantContext(tenantId));

        TripRepository trips = Mockito.mock(TripRepository.class);
        whenStatusCount(trips, tenantId, TripStatus.DISPATCHED, 3L);
        whenStatusCount(trips, tenantId, TripStatus.DEPARTED, 4L);
        whenStatusCount(trips, tenantId, TripStatus.ARRIVED, 8L);
        whenStatusCount(trips, tenantId, TripStatus.CANCELLED, 1L);
        when(trips.sumPassengerCountForPeriod(
                Mockito.eq(tenantId),
                ArgumentMatchers.any(Instant.class),
                ArgumentMatchers.any(Instant.class)
        )).thenReturn(217L);
        when(trips.sumRevenueForPeriod(
                Mockito.eq(tenantId),
                ArgumentMatchers.any(Instant.class),
                ArgumentMatchers.any(Instant.class)
        )).thenReturn(new BigDecimal("16275.00"));

        ReportingService service = new ReportingService(trips);
        DailyOperationsReportResponse response = service.dailyOperations(businessDate);

        assertThat(response.businessDate()).isEqualTo(businessDate);
        assertThat(response.dispatchedTrips()).isEqualTo(3L);
        assertThat(response.departedTrips()).isEqualTo(4L);
        assertThat(response.arrivedTrips()).isEqualTo(8L);
        assertThat(response.cancelledTrips()).isEqualTo(1L);
        assertThat(response.totalTrips()).isEqualTo(16L);
        assertThat(response.totalPassengers()).isEqualTo(217L);
        assertThat(response.totalRevenue()).isEqualByComparingTo("16275.00");
    }

    @Test
    void usesZeroValuesWhenNoTripsExistForDate() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        TripRepository trips = Mockito.mock(TripRepository.class);
        ReportingService service = new ReportingService(trips);

        DailyOperationsReportResponse response = service.dailyOperations(LocalDate.of(2026, 9, 1));

        assertThat(response.totalTrips()).isZero();
        assertThat(response.totalPassengers()).isZero();
        assertThat(response.totalRevenue()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void requiresTenantContext() {
        TripRepository trips = Mockito.mock(TripRepository.class);
        ReportingService service = new ReportingService(trips);

        assertThatThrownBy(() -> service.dailyOperations(LocalDate.of(2026, 9, 1)))
                .isInstanceOf(TenantContextRequiredException.class);
    }

    private void whenStatusCount(TripRepository trips, UUID tenantId, TripStatus status, long count) {
        when(trips.countByTenantIdAndStatusAndDispatchedAtGreaterThanEqualAndDispatchedAtLessThan(
                Mockito.eq(tenantId),
                Mockito.eq(status),
                ArgumentMatchers.any(Instant.class),
                ArgumentMatchers.any(Instant.class)
        )).thenReturn(count);
    }
}