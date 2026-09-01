package com.spheretech.taxisphere.compliance.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.compliance.api.ComplianceOverviewResponse;
import com.spheretech.taxisphere.driver.persistence.DriverRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContext;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import com.spheretech.taxisphere.vehicle.persistence.VehicleRepository;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ComplianceServiceTests {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void returnsTenantComplianceOverview() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        DriverRepository drivers = Mockito.mock(DriverRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        when(drivers.countByTenantIdAndLicenseExpiryDateBefore(Mockito.eq(tenantId), any(LocalDate.class))).thenReturn(2L);
        when(drivers.countByTenantIdAndLicenseExpiryDateBetween(
                Mockito.eq(tenantId),
                any(LocalDate.class),
                any(LocalDate.class)
        )).thenReturn(3L);
        when(drivers.countByTenantIdAndPdpExpiryDateBefore(Mockito.eq(tenantId), any(LocalDate.class))).thenReturn(4L);
        when(drivers.countByTenantIdAndPdpExpiryDateBetween(
                Mockito.eq(tenantId),
                any(LocalDate.class),
                any(LocalDate.class)
        )).thenReturn(5L);
        when(vehicles.countByTenantIdAndRoadworthyExpiryDateBefore(Mockito.eq(tenantId), any(LocalDate.class))).thenReturn(6L);
        when(vehicles.countByTenantIdAndRoadworthyExpiryDateBetween(
                Mockito.eq(tenantId),
                any(LocalDate.class),
                any(LocalDate.class)
        )).thenReturn(7L);
        when(vehicles.countByTenantIdAndInsuranceExpiryDateBefore(Mockito.eq(tenantId), any(LocalDate.class))).thenReturn(8L);
        when(vehicles.countByTenantIdAndInsuranceExpiryDateBetween(
                Mockito.eq(tenantId),
                any(LocalDate.class),
                any(LocalDate.class)
        )).thenReturn(9L);

        ComplianceService service = new ComplianceService(drivers, vehicles);
        ComplianceOverviewResponse response = service.overview(45);

        assertThat(response.warningThresholdDate()).isEqualTo(response.businessDate().plusDays(45));
        assertThat(response.expiredDriverLicenses()).isEqualTo(2L);
        assertThat(response.expiringDriverLicenses()).isEqualTo(3L);
        assertThat(response.expiredDriverPdps()).isEqualTo(4L);
        assertThat(response.expiringDriverPdps()).isEqualTo(5L);
        assertThat(response.expiredRoadworthyCertificates()).isEqualTo(6L);
        assertThat(response.expiringRoadworthyCertificates()).isEqualTo(7L);
        assertThat(response.expiredInsurancePolicies()).isEqualTo(8L);
        assertThat(response.expiringInsurancePolicies()).isEqualTo(9L);
        assertThat(response.totalExpiredItems()).isEqualTo(20L);
        assertThat(response.totalExpiringItems()).isEqualTo(24L);
    }

    @Test
    void defaultsWarningWindowToThirtyDays() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        DriverRepository drivers = Mockito.mock(DriverRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        ComplianceService service = new ComplianceService(drivers, vehicles);

        ComplianceOverviewResponse response = service.overview(null);

        assertThat(response.warningThresholdDate()).isEqualTo(response.businessDate().plusDays(30));
    }

    @Test
    void preventsNegativeWarningWindow() {
        UUID tenantId = UUID.randomUUID();
        TenantContextHolder.set(new TenantContext(tenantId));

        DriverRepository drivers = Mockito.mock(DriverRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        ComplianceService service = new ComplianceService(drivers, vehicles);

        ComplianceOverviewResponse response = service.overview(-10);

        assertThat(response.warningThresholdDate()).isEqualTo(response.businessDate());
    }

    @Test
    void requiresTenantContext() {
        DriverRepository drivers = Mockito.mock(DriverRepository.class);
        VehicleRepository vehicles = Mockito.mock(VehicleRepository.class);
        ComplianceService service = new ComplianceService(drivers, vehicles);

        assertThatThrownBy(() -> service.overview(30))
                .isInstanceOf(TenantContextRequiredException.class);
    }
}