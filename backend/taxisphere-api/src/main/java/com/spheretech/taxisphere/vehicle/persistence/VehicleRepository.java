package com.spheretech.taxisphere.vehicle.persistence;

import com.spheretech.taxisphere.vehicle.domain.Vehicle;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    List<Vehicle> findAllByTenantIdOrderByRegistrationNumberAsc(UUID tenantId);

    Optional<Vehicle> findByIdAndTenantId(UUID id, UUID tenantId);

    boolean existsByTenantIdAndRegistrationNumberIgnoreCase(UUID tenantId, String registrationNumber);

    boolean existsByTenantIdAndVinIgnoreCase(UUID tenantId, String vin);
}
