package com.spheretech.taxisphere.rank.persistence;

import com.spheretech.taxisphere.rank.domain.TaxiRank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiRankRepository extends JpaRepository<TaxiRank, UUID> {

    List<TaxiRank> findAllByTenantIdOrderByNameAsc(UUID tenantId);

    Optional<TaxiRank> findByIdAndTenantId(UUID id, UUID tenantId);

    boolean existsByTenantIdAndCodeIgnoreCase(UUID tenantId, String code);

    long countByTenantId(UUID tenantId);
}
