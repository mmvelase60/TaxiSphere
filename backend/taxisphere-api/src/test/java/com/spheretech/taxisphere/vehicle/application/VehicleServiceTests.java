package com.spheretech.taxisphere.vehicle.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.association.domain.AssociationStatus;
import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.vehicle.api.CreateVehicleRequest;
import com.spheretech.taxisphere.vehicle.domain.Vehicle;
import com.spheretech.taxisphere.vehicle.domain.VehicleStatus;
import com.spheretech.taxisphere.vehicle.persistence.VehicleRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VehicleServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void createsVehicleForCurrentTenantAssociation() {
        UUID tenantId = UUID.randomUUID();
        TaxiAssociation association = association(tenantId);
        TenantContextHolder.set(new TenantContext(tenantId));

        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.of(association));
        when(vehicles.save(Mockito.any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VehicleService service = new VehicleService(vehicles, associations);
        Vehicle vehicle = service.createVehicle(vehicleRequest());

        assertThat(vehicle.getTenantId()).isEqualTo(tenantId);
        assertThat(vehicle.getAssociationId()).isEqualTo(association.getId());
        assertThat(vehicle.getStatus()).isEqualTo(VehicleStatus.PENDING_VERIFICATION);
    }

    @Test
    void rejectsDuplicateRegistrationWithinTenant() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        when(vehicles.existsByTenantIdAndRegistrationNumberIgnoreCase(tenantId, "ABC-123-GP")).thenReturn(true);

        VehicleService service = new VehicleService(vehicles, Mockito.mock(TaxiAssociationRepository.class));

        assertThatThrownBy(() -> service.createVehicle(vehicleRequest()))
                .isInstanceOf(VehicleRegistrationAlreadyExistsException.class);
    }

    @Test
    void requiresAssociationBeforeVehicleCreation() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.empty());

        VehicleService service = new VehicleService(Mockito.mock(VehicleRepository.class), associations);

        assertThatThrownBy(() -> service.createVehicle(vehicleRequest()))
                .isInstanceOf(AssociationProfileRequiredException.class);
    }

    private CreateVehicleRequest vehicleRequest() {
        return new CreateVehicleRequest(
                "ABC-123-GP",
                "Toyota",
                "Quantum",
                2022,
                15,
                "VIN-123",
                LocalDate.now().plusYears(1),
                LocalDate.now().plusYears(1)
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
