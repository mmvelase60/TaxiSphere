package com.spheretech.taxisphere.compliance.application;

import com.spheretech.taxisphere.compliance.api.ComplianceOverviewResponse;
import com.spheretech.taxisphere.driver.persistence.DriverRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import com.spheretech.taxisphere.vehicle.persistence.VehicleRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComplianceService {

    private static final int DEFAULT_WARNING_DAYS = 30;

    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    public ComplianceService(DriverRepository driverRepository, VehicleRepository vehicleRepository) {
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional(readOnly = true)
    public ComplianceOverviewResponse overview(Integer warningDays) {
        UUID tenantId = currentTenantId();
        int resolvedWarningDays = warningDays == null ? DEFAULT_WARNING_DAYS : Math.max(warningDays, 0);
        LocalDate businessDate = LocalDate.now(ZoneOffset.UTC);
        LocalDate warningThresholdDate = businessDate.plusDays(resolvedWarningDays);

        long expiredDriverLicenses = driverRepository.countByTenantIdAndLicenseExpiryDateBefore(tenantId, businessDate);
        long expiringDriverLicenses = driverRepository.countByTenantIdAndLicenseExpiryDateBetween(
                tenantId,
                businessDate,
                warningThresholdDate
        );
        long expiredDriverPdps = driverRepository.countByTenantIdAndPdpExpiryDateBefore(tenantId, businessDate);
        long expiringDriverPdps = driverRepository.countByTenantIdAndPdpExpiryDateBetween(
                tenantId,
                businessDate,
                warningThresholdDate
        );
        long expiredRoadworthyCertificates = vehicleRepository.countByTenantIdAndRoadworthyExpiryDateBefore(
                tenantId,
                businessDate
        );
        long expiringRoadworthyCertificates = vehicleRepository.countByTenantIdAndRoadworthyExpiryDateBetween(
                tenantId,
                businessDate,
                warningThresholdDate
        );
        long expiredInsurancePolicies = vehicleRepository.countByTenantIdAndInsuranceExpiryDateBefore(
                tenantId,
                businessDate
        );
        long expiringInsurancePolicies = vehicleRepository.countByTenantIdAndInsuranceExpiryDateBetween(
                tenantId,
                businessDate,
                warningThresholdDate
        );

        return new ComplianceOverviewResponse(
                businessDate,
                warningThresholdDate,
                Instant.now(),
                expiredDriverLicenses,
                expiringDriverLicenses,
                expiredDriverPdps,
                expiringDriverPdps,
                expiredRoadworthyCertificates,
                expiringRoadworthyCertificates,
                expiredInsurancePolicies,
                expiringInsurancePolicies,
                expiredDriverLicenses + expiredDriverPdps + expiredRoadworthyCertificates + expiredInsurancePolicies,
                expiringDriverLicenses + expiringDriverPdps + expiringRoadworthyCertificates + expiringInsurancePolicies
        );
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}