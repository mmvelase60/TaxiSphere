package com.spheretech.taxisphere.vehicle.persistence;

import com.spheretech.taxisphere.vehicle.domain.Vehicle;
import com.spheretech.taxisphere.vehicle.domain.VehicleStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    List<Vehicle> findAllByTenantIdOrderByRegistrationNumberAsc(UUID tenantId);

    Optional<Vehicle> findByIdAndTenantId(UUID id, UUID tenantId);

    boolean existsByTenantIdAndRegistrationNumberIgnoreCase(UUID tenantId, String registrationNumber);

    boolean existsByTenantIdAndVinIgnoreCase(UUID tenantId, String vin);

    long countByTenantId(UUID tenantId);

    long countByTenantIdAndStatus(UUID tenantId, VehicleStatus status);

    long countByTenantIdAndRoadworthyExpiryDateBefore(UUID tenantId, LocalDate date);

    long countByTenantIdAndRoadworthyExpiryDateBetween(UUID tenantId, LocalDate startDate, LocalDate endDate);

    long countByTenantIdAndInsuranceExpiryDateBefore(UUID tenantId, LocalDate date);

    long countByTenantIdAndInsuranceExpiryDateBetween(UUID tenantId, LocalDate startDate, LocalDate endDate);
}
