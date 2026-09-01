package com.spheretech.taxisphere.trip.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.assignment.application.VehicleAssignmentNotFoundException;
import com.spheretech.taxisphere.assignment.domain.VehicleAssignment;
import com.spheretech.taxisphere.assignment.domain.VehicleAssignmentStatus;
import com.spheretech.taxisphere.assignment.persistence.VehicleAssignmentRepository;
import com.spheretech.taxisphere.route.application.TaxiRouteNotFoundException;
import com.spheretech.taxisphere.route.domain.TaxiRoute;
import com.spheretech.taxisphere.route.domain.TaxiRouteStatus;
import com.spheretech.taxisphere.route.persistence.TaxiRouteRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.trip.api.DispatchTripRequest;
import com.spheretech.taxisphere.trip.domain.Trip;
import com.spheretech.taxisphere.trip.domain.TripStatus;
import com.spheretech.taxisphere.trip.persistence.TripRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TripServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void dispatchesTripFromActiveAssignmentAndRoute() {
        UUID tenantId = UUID.randomUUID();
        UUID associationId = UUID.randomUUID();
        VehicleAssignment assignment = assignment(tenantId, associationId, VehicleAssignmentStatus.ACTIVE);
        TaxiRoute route = route(tenantId, associationId);
        TenantContextHolder.set(new TenantContext(tenantId));

        TripRepository trips = Mockito.mock(TripRepository.class);
        VehicleAssignmentRepository assignments = Mockito.mock(VehicleAssignmentRepository.class);
        TaxiRouteRepository routes = Mockito.mock(TaxiRouteRepository.class);
        when(assignments.findByIdAndTenantId(assignment.getId(), tenantId)).thenReturn(Optional.of(assignment));
        when(routes.findByIdAndTenantId(route.getId(), tenantId)).thenReturn(Optional.of(route));
        when(trips.save(Mockito.any(Trip.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TripService service = new TripService(trips, assignments, routes);
        Trip trip = service.dispatchTrip(new DispatchTripRequest(assignment.getId(), route.getId(), 12));

        assertThat(trip.getTenantId()).isEqualTo(tenantId);
        assertThat(trip.getAssociationId()).isEqualTo(associationId);
        assertThat(trip.getDriverId()).isEqualTo(assignment.getDriverId());
        assertThat(trip.getVehicleId()).isEqualTo(assignment.getVehicleId());
        assertThat(trip.getStatus()).isEqualTo(TripStatus.DISPATCHED);
        assertThat(trip.getFarePerPassenger()).isEqualByComparingTo("85.00");
        assertThat(trip.getTotalRevenue()).isEqualByComparingTo("1020.00");
    }

    @Test
    void rejectsMissingAssignment() {
        UUID tenantId = UUID.randomUUID();
        UUID assignmentId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        VehicleAssignmentRepository assignments = Mockito.mock(VehicleAssignmentRepository.class);
        when(assignments.findByIdAndTenantId(assignmentId, tenantId)).thenReturn(Optional.empty());

        TripService service = new TripService(
                Mockito.mock(TripRepository.class),
                assignments,
                Mockito.mock(TaxiRouteRepository.class)
        );

        assertThatThrownBy(() -> service.dispatchTrip(new DispatchTripRequest(
                assignmentId,
                UUID.randomUUID(),
                10
        ))).isInstanceOf(VehicleAssignmentNotFoundException.class);
    }

    @Test
    void rejectsMissingRoute() {
        UUID tenantId = UUID.randomUUID();
        UUID associationId = UUID.randomUUID();
        VehicleAssignment assignment = assignment(tenantId, associationId, VehicleAssignmentStatus.ACTIVE);
        UUID routeId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        VehicleAssignmentRepository assignments = Mockito.mock(VehicleAssignmentRepository.class);
        TaxiRouteRepository routes = Mockito.mock(TaxiRouteRepository.class);
        when(assignments.findByIdAndTenantId(assignment.getId(), tenantId)).thenReturn(Optional.of(assignment));
        when(routes.findByIdAndTenantId(routeId, tenantId)).thenReturn(Optional.empty());

        TripService service = new TripService(Mockito.mock(TripRepository.class), assignments, routes);

        assertThatThrownBy(() -> service.dispatchTrip(new DispatchTripRequest(
                assignment.getId(),
                routeId,
                10
        ))).isInstanceOf(TaxiRouteNotFoundException.class);
    }

    @Test
    void rejectsInactiveAssignment() {
        UUID tenantId = UUID.randomUUID();
        UUID associationId = UUID.randomUUID();
        VehicleAssignment assignment = assignment(tenantId, associationId, VehicleAssignmentStatus.ENDED);
        TenantContextHolder.set(new TenantContext(tenantId));
        VehicleAssignmentRepository assignments = Mockito.mock(VehicleAssignmentRepository.class);
        when(assignments.findByIdAndTenantId(assignment.getId(), tenantId)).thenReturn(Optional.of(assignment));

        TripService service = new TripService(
                Mockito.mock(TripRepository.class),
                assignments,
                Mockito.mock(TaxiRouteRepository.class)
        );

        assertThatThrownBy(() -> service.dispatchTrip(new DispatchTripRequest(
                assignment.getId(),
                UUID.randomUUID(),
                10
        ))).isInstanceOf(VehicleAssignmentNotActiveException.class);
    }

    private VehicleAssignment assignment(
            UUID tenantId,
            UUID associationId,
            VehicleAssignmentStatus status
    ) {
        return new VehicleAssignment(
                UUID.randomUUID(),
                tenantId,
                associationId,
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDate.now(),
                status
        );
    }

    private TaxiRoute route(UUID tenantId, UUID associationId) {
        return new TaxiRoute(
                UUID.randomUUID(),
                tenantId,
                associationId,
                null,
                "PTA-JHB",
                "Pretoria",
                "Johannesburg",
                new BigDecimal("85.00"),
                new BigDecimal("62.50"),
                75,
                TaxiRouteStatus.SETUP
        );
    }
}
