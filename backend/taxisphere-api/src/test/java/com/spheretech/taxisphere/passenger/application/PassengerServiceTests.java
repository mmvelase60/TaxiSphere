package com.spheretech.taxisphere.passenger.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.association.domain.AssociationStatus;
import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.passenger.api.CreatePassengerRequest;
import com.spheretech.taxisphere.passenger.domain.Passenger;
import com.spheretech.taxisphere.passenger.domain.PassengerStatus;
import com.spheretech.taxisphere.passenger.persistence.PassengerRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PassengerServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void createsPassengerForCurrentTenantAssociation() {
        UUID tenantId = UUID.randomUUID();
        UUID userAccountId = UUID.randomUUID();
        TaxiAssociation association = association(tenantId);
        TenantContextHolder.set(new TenantContext(tenantId));

        PassengerRepository passengers = Mockito.mock(PassengerRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.of(association));
        when(passengers.save(Mockito.any(Passenger.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PassengerService service = new PassengerService(passengers, associations);
        Passenger passenger = service.createPassenger(passengerRequest(userAccountId));

        assertThat(passenger.getTenantId()).isEqualTo(tenantId);
        assertThat(passenger.getAssociationId()).isEqualTo(association.getId());
        assertThat(passenger.getUserAccountId()).isEqualTo(userAccountId);
        assertThat(passenger.getStatus()).isEqualTo(PassengerStatus.ACTIVE);
    }

    @Test
    void rejectsDuplicatePhoneWithinTenant() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        PassengerRepository passengers = Mockito.mock(PassengerRepository.class);
        when(passengers.existsByTenantIdAndPhoneNumberIgnoreCase(tenantId, "+27820000000")).thenReturn(true);

        PassengerService service = new PassengerService(passengers, Mockito.mock(TaxiAssociationRepository.class));

        assertThatThrownBy(() -> service.createPassenger(passengerRequest(null)))
                .isInstanceOf(PassengerPhoneAlreadyExistsException.class);
    }

    @Test
    void rejectsDuplicateEmailWithinTenant() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        PassengerRepository passengers = Mockito.mock(PassengerRepository.class);
        when(passengers.existsByTenantIdAndEmailIgnoreCase(tenantId, "passenger@example.com")).thenReturn(true);

        PassengerService service = new PassengerService(passengers, Mockito.mock(TaxiAssociationRepository.class));

        assertThatThrownBy(() -> service.createPassenger(passengerRequest(null)))
                .isInstanceOf(PassengerEmailAlreadyExistsException.class);
    }

    @Test
    void requiresAssociationBeforePassengerCreation() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.empty());

        PassengerService service = new PassengerService(Mockito.mock(PassengerRepository.class), associations);

        assertThatThrownBy(() -> service.createPassenger(passengerRequest(null)))
                .isInstanceOf(AssociationProfileRequiredException.class);
    }

    @Test
    void requiresTenantContext() {
        PassengerService service = new PassengerService(
                Mockito.mock(PassengerRepository.class),
                Mockito.mock(TaxiAssociationRepository.class)
        );

        assertThatThrownBy(service::findAllForCurrentTenant)
                .isInstanceOf(TenantContextRequiredException.class);
    }

    private CreatePassengerRequest passengerRequest(UUID userAccountId) {
        return new CreatePassengerRequest(
                userAccountId,
                "Lerato",
                "Molefe",
                "+27820000000",
                "passenger@example.com"
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
}