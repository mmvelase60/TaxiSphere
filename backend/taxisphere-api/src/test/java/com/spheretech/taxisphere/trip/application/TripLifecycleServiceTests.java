package com.spheretech.taxisphere.trip.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.trip.domain.Trip;
import com.spheretech.taxisphere.trip.domain.TripStatus;
import com.spheretech.taxisphere.trip.persistence.TripRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TripLifecycleServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void departsDispatchedTrip() {
        UUID tenantId = UUID.randomUUID();
        Trip trip = trip(tenantId, TripStatus.DISPATCHED);
        TenantContextHolder.set(new TenantContext(tenantId));

        TripRepository trips = Mockito.mock(TripRepository.class);
        when(trips.findByIdAndTenantId(trip.getId(), tenantId)).thenReturn(Optional.of(trip));
        when(trips.save(Mockito.any(Trip.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TripService service = new TripService(trips, null, null);
        Trip updated = service.departTrip(trip.getId());

        assertThat(updated.getStatus()).isEqualTo(TripStatus.DEPARTED);
        assertThat(updated.getDepartedAt()).isNotNull();
    }

    @Test
    void arrivesDepartedTrip() {
        UUID tenantId = UUID.randomUUID();
        Trip trip = trip(tenantId, TripStatus.DISPATCHED);
        trip.markDeparted(Instant.now());
        TenantContextHolder.set(new TenantContext(tenantId));

        TripRepository trips = Mockito.mock(TripRepository.class);
        when(trips.findByIdAndTenantId(trip.getId(), tenantId)).thenReturn(Optional.of(trip));
        when(trips.save(Mockito.any(Trip.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TripService service = new TripService(trips, null, null);
        Trip updated = service.arriveTrip(trip.getId());

        assertThat(updated.getStatus()).isEqualTo(TripStatus.ARRIVED);
        assertThat(updated.getArrivedAt()).isNotNull();
    }

    @Test
    void rejectsArrivingBeforeDeparture() {
        UUID tenantId = UUID.randomUUID();
        Trip trip = trip(tenantId, TripStatus.DISPATCHED);
        TenantContextHolder.set(new TenantContext(tenantId));

        TripRepository trips = Mockito.mock(TripRepository.class);
        when(trips.findByIdAndTenantId(trip.getId(), tenantId)).thenReturn(Optional.of(trip));

        TripService service = new TripService(trips, null, null);

        assertThatThrownBy(() -> service.arriveTrip(trip.getId()))
                .isInstanceOf(InvalidTripStatusTransitionException.class);
    }

    @Test
    void cancelsDispatchedTrip() {
        UUID tenantId = UUID.randomUUID();
        Trip trip = trip(tenantId, TripStatus.DISPATCHED);
        TenantContextHolder.set(new TenantContext(tenantId));

        TripRepository trips = Mockito.mock(TripRepository.class);
        when(trips.findByIdAndTenantId(trip.getId(), tenantId)).thenReturn(Optional.of(trip));
        when(trips.save(Mockito.any(Trip.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TripService service = new TripService(trips, null, null);
        Trip updated = service.cancelTrip(trip.getId());

        assertThat(updated.getStatus()).isEqualTo(TripStatus.CANCELLED);
    }

    private Trip trip(UUID tenantId, TripStatus status) {
        return new Trip(
                UUID.randomUUID(),
                tenantId,
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                12,
                new BigDecimal("85.00"),
                new BigDecimal("1020.00"),
                status,
                Instant.now()
        );
    }
}
