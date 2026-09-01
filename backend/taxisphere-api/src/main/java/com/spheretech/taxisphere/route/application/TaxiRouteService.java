package com.spheretech.taxisphere.route.application;

import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.rank.persistence.TaxiRankRepository;
import com.spheretech.taxisphere.route.api.CreateTaxiRouteRequest;
import com.spheretech.taxisphere.route.domain.TaxiRoute;
import com.spheretech.taxisphere.route.domain.TaxiRouteStatus;
import com.spheretech.taxisphere.route.persistence.TaxiRouteRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaxiRouteService {

    private final TaxiRouteRepository routeRepository;
    private final TaxiAssociationRepository associationRepository;
    private final TaxiRankRepository rankRepository;

    public TaxiRouteService(
            TaxiRouteRepository routeRepository,
            TaxiAssociationRepository associationRepository,
            TaxiRankRepository rankRepository
    ) {
        this.routeRepository = routeRepository;
        this.associationRepository = associationRepository;
        this.rankRepository = rankRepository;
    }

    @Transactional(readOnly = true)
    public List<TaxiRoute> findAllForCurrentTenant() {
        return routeRepository.findAllByTenantIdOrderByOriginAscDestinationAsc(currentTenantId());
    }

    @Transactional(readOnly = true)
    public TaxiRoute findByIdForCurrentTenant(UUID routeId) {
        UUID tenantId = currentTenantId();
        return routeRepository.findByIdAndTenantId(routeId, tenantId)
                .orElseThrow(() -> new TaxiRouteNotFoundException(routeId));
    }

    @Transactional
    public TaxiRoute createRoute(CreateTaxiRouteRequest request) {
        UUID tenantId = currentTenantId();

        if (routeRepository.existsByTenantIdAndCodeIgnoreCase(tenantId, request.code())) {
            throw new TaxiRouteCodeAlreadyExistsException(request.code());
        }

        TaxiAssociation association = associationRepository.findByTenantId(tenantId)
                .orElseThrow(com.spheretech.taxisphere.rank.application.AssociationProfileRequiredException::new);

        if (request.originRankId() != null && rankRepository.findByIdAndTenantId(request.originRankId(), tenantId).isEmpty()) {
            throw new OriginRankNotFoundException(request.originRankId());
        }

        TaxiRoute route = new TaxiRoute(
                UUID.randomUUID(),
                tenantId,
                association.getId(),
                request.originRankId(),
                request.code(),
                request.origin(),
                request.destination(),
                request.fare(),
                request.distanceKm(),
                request.estimatedMinutes(),
                TaxiRouteStatus.SETUP
        );

        return routeRepository.save(route);
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}
