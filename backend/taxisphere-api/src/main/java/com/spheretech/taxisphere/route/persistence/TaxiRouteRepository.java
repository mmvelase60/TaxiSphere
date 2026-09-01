package com.spheretech.taxisphere.route.persistence;

import com.spheretech.taxisphere.route.domain.TaxiRoute;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiRouteRepository extends JpaRepository<TaxiRoute, UUID> {

    List<TaxiRoute> findAllByTenantIdOrderByOriginAscDestinationAsc(UUID tenantId);

    Optional<TaxiRoute> findByIdAndTenantId(UUID id, UUID tenantId);

    boolean existsByTenantIdAndCodeIgnoreCase(UUID tenantId, String code);

    long countByTenantId(UUID tenantId);
}
