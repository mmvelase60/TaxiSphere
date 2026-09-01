package com.spheretech.taxisphere.assignment.persistence;

import com.spheretech.taxisphere.assignment.domain.VehicleAssignment;
import com.spheretech.taxisphere.assignment.domain.VehicleAssignmentStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleAssignmentRepository extends JpaRepository<VehicleAssignment, UUID> {

    List<VehicleAssignment> findAllByTenantIdOrderByCreatedAtDesc(UUID tenantId);

    Optional<VehicleAssignment> findByIdAndTenantId(UUID id, UUID tenantId);

    boolean existsByTenantIdAndDriverIdAndStatus(UUID tenantId, UUID driverId, VehicleAssignmentStatus status);

    boolean existsByTenantIdAndVehicleIdAndStatus(UUID tenantId, UUID vehicleId, VehicleAssignmentStatus status);
}
