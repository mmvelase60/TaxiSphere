package com.spheretech.taxisphere.rank.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.association.domain.AssociationStatus;
import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.rank.api.CreateTaxiRankRequest;
import com.spheretech.taxisphere.rank.domain.TaxiRank;
import com.spheretech.taxisphere.rank.domain.TaxiRankStatus;
import com.spheretech.taxisphere.rank.persistence.TaxiRankRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaxiRankServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void createsRankForCurrentTenantAssociation() {
        UUID tenantId = UUID.randomUUID();
        TaxiAssociation association = new TaxiAssociation(
                UUID.randomUUID(),
                tenantId,
                "Pretoria Taxi Association",
                "PTA-001",
                "office@pta-taxi.example",
                "+27120000000",
                AssociationStatus.SETUP
        );
        TenantContextHolder.set(new TenantContext(tenantId));

        TaxiRankRepository ranks = Mockito.mock(TaxiRankRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.of(association));
        when(ranks.save(Mockito.any(TaxiRank.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaxiRankService service = new TaxiRankService(ranks, associations);
        TaxiRank rank = service.createRank(new CreateTaxiRankRequest(
                "Pretoria Station Rank",
                "PTA-STATION",
                "Pretoria Station, Pretoria Central",
                "Pretoria",
                "Gauteng",
                120,
                new BigDecimal("-25.7545"),
                new BigDecimal("28.1892"),
                "05:00-22:00"
        ));

        assertThat(rank.getTenantId()).isEqualTo(tenantId);
        assertThat(rank.getAssociationId()).isEqualTo(association.getId());
        assertThat(rank.getStatus()).isEqualTo(TaxiRankStatus.SETUP);
    }

    @Test
    void rejectsDuplicateRankCodeWithinTenant() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        TaxiRankRepository ranks = Mockito.mock(TaxiRankRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(ranks.existsByTenantIdAndCodeIgnoreCase(tenantId, "PTA-STATION")).thenReturn(true);

        TaxiRankService service = new TaxiRankService(ranks, associations);

        assertThatThrownBy(() -> service.createRank(new CreateTaxiRankRequest(
                "Pretoria Station Rank",
                "PTA-STATION",
                "Pretoria Station, Pretoria Central",
                "Pretoria",
                "Gauteng",
                120,
                null,
                null,
                "05:00-22:00"
        ))).isInstanceOf(TaxiRankCodeAlreadyExistsException.class);
    }

    @Test
    void requiresAssociationBeforeRankCreation() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        TaxiRankRepository ranks = Mockito.mock(TaxiRankRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.empty());

        TaxiRankService service = new TaxiRankService(ranks, associations);

        assertThatThrownBy(() -> service.createRank(new CreateTaxiRankRequest(
                "Pretoria Station Rank",
                "PTA-STATION",
                "Pretoria Station, Pretoria Central",
                "Pretoria",
                "Gauteng",
                120,
                null,
                null,
                "05:00-22:00"
        ))).isInstanceOf(AssociationProfileRequiredException.class);
    }
}
