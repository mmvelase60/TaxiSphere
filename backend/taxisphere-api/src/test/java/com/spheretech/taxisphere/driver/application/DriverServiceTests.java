package com.spheretech.taxisphere.driver.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.association.domain.AssociationStatus;
import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.driver.api.CreateDriverRequest;
import com.spheretech.taxisphere.driver.domain.Driver;
import com.spheretech.taxisphere.driver.domain.DriverStatus;
import com.spheretech.taxisphere.driver.persistence.DriverRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DriverServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void createsDriverForCurrentTenantAssociation() {
        UUID tenantId = UUID.randomUUID();
        TaxiAssociation association = association(tenantId);
        TenantContextHolder.set(new TenantContext(tenantId));

        DriverRepository drivers = Mockito.mock(DriverRepository.class);
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.of(association));
        when(drivers.save(Mockito.any(Driver.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DriverService service = new DriverService(drivers, associations);
        Driver driver = service.createDriver(driverRequest());

        assertThat(driver.getTenantId()).isEqualTo(tenantId);
        assertThat(driver.getAssociationId()).isEqualTo(association.getId());
        assertThat(driver.getStatus()).isEqualTo(DriverStatus.PENDING_VERIFICATION);
    }

    @Test
    void rejectsDuplicateLicenseWithinTenant() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        DriverRepository drivers = Mockito.mock(DriverRepository.class);
        when(drivers.existsByTenantIdAndLicenseNumberIgnoreCase(tenantId, "LIC-123")).thenReturn(true);

        DriverService service = new DriverService(drivers, Mockito.mock(TaxiAssociationRepository.class));

        assertThatThrownBy(() -> service.createDriver(driverRequest()))
                .isInstanceOf(DriverLicenseAlreadyExistsException.class);
    }

    @Test
    void requiresAssociationBeforeDriverCreation() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));
        TaxiAssociationRepository associations = Mockito.mock(TaxiAssociationRepository.class);
        when(associations.findByTenantId(tenantId)).thenReturn(Optional.empty());

        DriverService service = new DriverService(Mockito.mock(DriverRepository.class), associations);

        assertThatThrownBy(() -> service.createDriver(driverRequest()))
                .isInstanceOf(AssociationProfileRequiredException.class);
    }

    private CreateDriverRequest driverRequest() {
        return new CreateDriverRequest(
                "Thabo",
                "Mokoena",
                "+27820000000",
                "thabo.mokoena@example.com",
                "LIC-123",
                "PDP-123",
                LocalDate.now().plusYears(2),
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
