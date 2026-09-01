package com.spheretech.taxisphere.route.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.association.domain.AssociationStatus;
import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.rank.domain.TaxiRank;
import com.spheretech.taxisphere.rank.domain.TaxiRankStatus;
import com.spheretech.taxisphere.rank.persistence.TaxiRankRepository;
import com.spheretech.taxisphere.route.api.CreateTaxiRouteRequest;
import com.spheretech.taxisphere.route.domain.TaxiRoute;
import com.spheretech.taxisphere.route.domain.TaxiRouteStatus;
import com.spheretech.taxisphere.route.persistence.TaxiRouteRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaxiRouteServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void createsRouteForCurrentTenantAssociation() {
        UUID tenantId = UUID.randomUUID();
        UUID rankId = UUID.randomUUID();
        TaxiAssociation association = association(tenantId);
        TenantContextHolder.set(new TenantContext(tenantId));

        TaxiRouteRepository routes = Mockito.mock(TaxiRouteRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        TaxiRankRepository ranks = Mockito.mock(TaxiRankRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.of(association));
        when(ranks.findByIdAndTenantId(rankId, tenantId)).thenReturn(Optional.of(rank(tenantId, association.getId(), rankId)));
        when(routes.save(Mockito.any(TaxiRoute.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaxiRouteService service = new TaxiRouteService(routes, associations, ranks);
        TaxiRoute route = service.createRoute(new CreateTaxiRouteRequest(
                rankId,
                "PTA-JHB",
                "Pretoria",
                "Johannesburg",
                new BigDecimal("85.00"),
                new BigDecimal("62.50"),
                75
        ));

        assertThat(route.getTenantId()).isEqualTo(tenantId);
        assertThat(route.getAssociationId()).isEqualTo(association.getId());
        assertThat(route.getOriginRankId()).isEqualTo(rankId);
        assertThat(route.getStatus()).isEqualTo(TaxiRouteStatus.SETUP);
    }

    @Test
    void rejectsDuplicateRouteCodeWithinTenant() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        TaxiRouteRepository routes = Mockito.mock(TaxiRouteRepository.class);
        when(routes.existsByTenantIdAndCodeIgnoreCase(tenantId, "PTA-JHB")).thenReturn(true);

        TaxiRouteService service = new TaxiRouteService(
                routes,
                Mockito.mock(TaxiAssociationRepository.class),
                Mockito.mock(TaxiRankRepository.class)
        );

        assertThatThrownBy(() -> service.createRoute(routeRequest(null)))
                .isInstanceOf(TaxiRouteCodeAlreadyExistsException.class);
    }

    @Test
    void rejectsOriginRankFromAnotherTenant() {
        UUID tenantId = UUID.randomUUID();
        UUID rankId = UUID.randomUUID();
        TaxiAssociation association = association(tenantId);
        TenantContextHolder.set(new TenantContext(tenantId));

        TaxiRouteRepository routes = Mockito.mock(TaxiRouteRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        TaxiRankRepository ranks = Mockito.mock(TaxiRankRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.of(association));
        when(ranks.findByIdAndTenantId(rankId, tenantId)).thenReturn(Optional.empty());

        TaxiRouteService service = new TaxiRouteService(routes, associations, ranks);

        assertThatThrownBy(() -> service.createRoute(routeRequest(rankId)))
                .isInstanceOf(OriginRankNotFoundException.class);
    }

    private CreateTaxiRouteRequest routeRequest(UUID rankId) {
        return new CreateTaxiRouteRequest(
                rankId,
                "PTA-JHB",
                "Pretoria",
                "Johannesburg",
                new BigDecimal("85.00"),
                new BigDecimal("62.50"),
                75
        );
    }

    private TaxiAssociation association(UUID tenantId) {
        return new TaxiAssociation(
                UUID.randomUUID(),
                tenantId,
                "Pretoria Taxi Association",
                "PTA-001",
                "office@pta-taxi.example",
                "+27120000000",
                AssociationStatus.SETUP
        );
    }

    private TaxiRank rank(UUID tenantId, UUID associationId, UUID rankId) {
        return new TaxiRank(
                rankId,
                tenantId,
                associationId,
                "Pretoria Station Rank",
                "PTA-STATION",
                "Pretoria Station, Pretoria Central",
                "Pretoria",
                "Gauteng",
                120,
                null,
                null,
                "05:00-22:00",
                TaxiRankStatus.SETUP
        );
    }
}
