package com.spheretech.taxisphere.driver.persistence;

import com.spheretech.taxisphere.driver.domain.Driver;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, UUID> {

    List<Driver> findAllByTenantIdOrderByLastNameAscFirstNameAsc(UUID tenantId);

    Optional<Driver> findByIdAndTenantId(UUID id, UUID tenantId);

    boolean existsByTenantIdAndLicenseNumberIgnoreCase(UUID tenantId, String licenseNumber);

    boolean existsByTenantIdAndPdpNumberIgnoreCase(UUID tenantId, String pdpNumber);
}
