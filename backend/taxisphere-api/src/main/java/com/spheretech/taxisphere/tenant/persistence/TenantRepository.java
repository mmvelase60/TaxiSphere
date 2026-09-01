package com.spheretech.taxisphere.tenant.persistence;

import com.spheretech.taxisphere.tenant.domain.Tenant;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {

    boolean existsByNameIgnoreCase(String name);
}
