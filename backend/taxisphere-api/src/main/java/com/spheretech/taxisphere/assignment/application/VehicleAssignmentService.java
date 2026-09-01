package com.spheretech.taxisphere.assignment.application;

import com.spheretech.taxisphere.assignment.api.CreateVehicleAssignmentRequest;
import com.spheretech.taxisphere.assignment.domain.VehicleAssignment;
import com.spheretech.taxisphere.assignment.domain.VehicleAssignmentStatus;
import com.spheretech.taxisphere.assignment.persistence.VehicleAssignmentRepository;
import com.spheretech.taxisphere.driver.domain.Driver;
import com.spheretech.taxisphere.driver.application.DriverNotFoundException;
import com.spheretech.taxisphere.driver.persistence.DriverRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import com.spheretech.taxisphere.vehicle.domain.Vehicle;
import com.spheretech.taxisphere.vehicle.application.VehicleNotFoundException;
import com.spheretech.taxisphere.vehicle.persistence.VehicleRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleAssignmentService {

    private final VehicleAssignmentRepository assignmentRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    public VehicleAssignmentService(
            VehicleAssignmentRepository assignmentRepository,
            DriverRepository driverRepository,
            VehicleRepository vehicleRepository
    ) {
        this.assignmentRepository = assignmentRepository;
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional(readOnly = true)
    public List<VehicleAssignment> findAllForCurrentTenant() {
        return assignmentRepository.findAllByTenantIdOrderByCreatedAtDesc(currentTenantId());
    }

    @Transactional(readOnly = true)
    public VehicleAssignment findByIdForCurrentTenant(UUID assignmentId) {
        UUID tenantId = currentTenantId();
        return assignmentRepository.findByIdAndTenantId(assignmentId, tenantId)
                .orElseThrow(() -> new VehicleAssignmentNotFoundException(assignmentId));
    }

    @Transactional
    public VehicleAssignment createAssignment(CreateVehicleAssignmentRequest request) {
        UUID tenantId = currentTenantId();

        Driver driver = driverRepository.findByIdAndTenantId(request.driverId(), tenantId)
                .orElseThrow(() -> new DriverNotFoundException(request.driverId()));
        Vehicle vehicle = vehicleRepository.findByIdAndTenantId(request.vehicleId(), tenantId)
                .orElseThrow(() -> new VehicleNotFoundException(request.vehicleId()));

        if (assignmentRepository.existsByTenantIdAndDriverIdAndStatus(
                tenantId,
                request.driverId(),
                VehicleAssignmentStatus.ACTIVE
        )) {
            throw new DriverAlreadyAssignedException(request.driverId());
        }

        if (assignmentRepository.existsByTenantIdAndVehicleIdAndStatus(
                tenantId,
                request.vehicleId(),
                VehicleAssignmentStatus.ACTIVE
        )) {
            throw new VehicleAlreadyAssignedException(request.vehicleId());
        }

        VehicleAssignment assignment = new VehicleAssignment(
                UUID.randomUUID(),
                tenantId,
                driver.getAssociationId(),
                driver.getId(),
                vehicle.getId(),
                request.assignedDate(),
                VehicleAssignmentStatus.ACTIVE
        );

        return assignmentRepository.save(assignment);
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}
