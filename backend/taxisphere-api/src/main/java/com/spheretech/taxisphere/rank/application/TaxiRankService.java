package com.spheretech.taxisphere.rank.application;

import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.rank.api.CreateTaxiRankRequest;
import com.spheretech.taxisphere.rank.domain.TaxiRank;
import com.spheretech.taxisphere.rank.domain.TaxiRankStatus;
import com.spheretech.taxisphere.rank.persistence.TaxiRankRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaxiRankService {

    private final TaxiRankRepository rankRepository;
    private final TaxiAssociationRepository associationRepository;

    public TaxiRankService(
            TaxiRankRepository rankRepository,
            TaxiAssociationRepository associationRepository
    ) {
        this.rankRepository = rankRepository;
        this.associationRepository = associationRepository;
    }

    @Transactional(readOnly = true)
    public List<TaxiRank> findAllForCurrentTenant() {
        return rankRepository.findAllByTenantIdOrderByNameAsc(currentTenantId());
    }

    @Transactional(readOnly = true)
    public TaxiRank findByIdForCurrentTenant(UUID rankId) {
        UUID tenantId = currentTenantId();
        return rankRepository.findByIdAndTenantId(rankId, tenantId)
                .orElseThrow(() -> new TaxiRankNotFoundException(rankId));
    }

    @Transactional
    public TaxiRank createRank(CreateTaxiRankRequest request) {
        UUID tenantId = currentTenantId();

        if (rankRepository.existsByTenantIdAndCodeIgnoreCase(tenantId, request.code())) {
            throw new TaxiRankCodeAlreadyExistsException(request.code());
        }

        TaxiAssociation association = associationRepository.findByTenantId(tenantId)
                .orElseThrow(AssociationProfileRequiredException::new);

        TaxiRank rank = new TaxiRank(
                UUID.randomUUID(),
                tenantId,
                association.getId(),
                request.name(),
                request.code(),
                request.address(),
                request.city(),
                request.province(),
                request.capacity(),
                request.latitude(),
                request.longitude(),
                request.operatingHours(),
                TaxiRankStatus.SETUP
        );

        return rankRepository.save(rank);
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}
