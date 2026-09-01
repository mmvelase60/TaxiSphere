package com.spheretech.taxisphere.trip.application;

import com.spheretech.taxisphere.assignment.application.VehicleAssignmentNotFoundException;
import com.spheretech.taxisphere.assignment.domain.VehicleAssignment;
import com.spheretech.taxisphere.assignment.domain.VehicleAssignmentStatus;
import com.spheretech.taxisphere.assignment.persistence.VehicleAssignmentRepository;
import com.spheretech.taxisphere.route.application.TaxiRouteNotFoundException;
import com.spheretech.taxisphere.route.domain.TaxiRoute;
import com.spheretech.taxisphere.route.persistence.TaxiRouteRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import com.spheretech.taxisphere.trip.api.DispatchTripRequest;
import com.spheretech.taxisphere.trip.domain.Trip;
import com.spheretech.taxisphere.trip.domain.TripStatus;
import com.spheretech.taxisphere.trip.persistence.TripRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final VehicleAssignmentRepository assignmentRepository;
    private final TaxiRouteRepository routeRepository;

    public TripService(
            TripRepository tripRepository,
            VehicleAssignmentRepository assignmentRepository,
            TaxiRouteRepository routeRepository
    ) {
        this.tripRepository = tripRepository;
        this.assignmentRepository = assignmentRepository;
        this.routeRepository = routeRepository;
    }

    @Transactional(readOnly = true)
    public List<Trip> findAllForCurrentTenant() {
        return tripRepository.findAllByTenantIdOrderByDispatchedAtDesc(currentTenantId());
    }

    @Transactional(readOnly = true)
    public Trip findByIdForCurrentTenant(UUID tripId) {
        UUID tenantId = currentTenantId();
        return tripRepository.findByIdAndTenantId(tripId, tenantId)
                .orElseThrow(() -> new TripNotFoundException(tripId));
    }

    @Transactional
    public Trip dispatchTrip(DispatchTripRequest request) {
        UUID tenantId = currentTenantId();

        VehicleAssignment assignment = assignmentRepository.findByIdAndTenantId(request.vehicleAssignmentId(), tenantId)
                .orElseThrow(() -> new VehicleAssignmentNotFoundException(request.vehicleAssignmentId()));

        if (assignment.getStatus() != VehicleAssignmentStatus.ACTIVE) {
            throw new VehicleAssignmentNotActiveException(request.vehicleAssignmentId());
        }

        TaxiRoute route = routeRepository.findByIdAndTenantId(request.routeId(), tenantId)
                .orElseThrow(() -> new TaxiRouteNotFoundException(request.routeId()));

        BigDecimal farePerPassenger = route.getFare();
        BigDecimal totalRevenue = farePerPassenger.multiply(BigDecimal.valueOf(request.passengerCount()));

        Trip trip = new Trip(
                UUID.randomUUID(),
                tenantId,
                assignment.getAssociationId(),
                assignment.getId(),
                assignment.getDriverId(),
                assignment.getVehicleId(),
                route.getId(),
                request.passengerCount(),
                farePerPassenger,
                totalRevenue,
                TripStatus.DISPATCHED,
                Instant.now()
        );

        return tripRepository.save(trip);
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}
