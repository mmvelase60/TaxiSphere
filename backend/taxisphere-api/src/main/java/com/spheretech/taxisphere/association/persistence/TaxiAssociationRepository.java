package com.spheretech.taxisphere.association.persistence;

import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiAssociationRepository extends JpaRepository<TaxiAssociation, UUID> {

    Optional<TaxiAssociation> findByTenantId(UUID tenantId);

    boolean existsByTenantId(UUID tenantId);

    boolean existsByRegistrationNumberIgnoreCase(String registrationNumber);
}
