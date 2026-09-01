package com.spheretech.taxisphere.association.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.association.api.CreateAssociationRequest;
import com.spheretech.taxisphere.association.domain.AssociationStatus;
import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AssociationServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void createsAssociationForCurrentTenant() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        TaxiAssociationRepository repository = Mockito.mock(TaxiAssociationRepository.class);
        when(repository.save(Mockito.any(TaxiAssociation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AssociationService service = new AssociationService(repository);
        TaxiAssociation association = service.createAssociation(new CreateAssociationRequest(
                "Pretoria Taxi Association",
                "PTA-001",
                "office@pta-taxi.example",
                "+27120000000"
        ));

        assertThat(association.getTenantId()).isEqualTo(tenantId);
        assertThat(association.getStatus()).isEqualTo(AssociationStatus.SETUP);
    }

    @Test
    void requiresTenantContext() {
        AssociationService service = new AssociationService(Mockito.mock(TaxiAssociationRepository.class));

        assertThatThrownBy(() -> service.getCurrentTenantAssociation())
                .isInstanceOf(com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException.class);
    }

    @Test
    void returnsCurrentTenantAssociation() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        TaxiAssociation expected = new TaxiAssociation(
                UUID.randomUUID(),
                tenantId,
                "Pretoria Taxi Association",
                "PTA-001",
                "office@pta-taxi.example",
                "+27120000000",
                AssociationStatus.SETUP
        );
        TaxiAssociationRepository repository = Mockito.mock(TaxiAssociationRepository.class);
        when(repository.findByTenantId(tenantId)).thenReturn(Optional.of(expected));

        AssociationService service = new AssociationService(repository);

        assertThat(service.getCurrentTenantAssociation()).isEqualTo(expected);
    }
}
