package com.spheretech.taxisphere.compliance.api;

import java.time.Instant;
import java.time.LocalDate;

public record ComplianceOverviewResponse(
        LocalDate businessDate,
        LocalDate warningThresholdDate,
        Instant generatedAt,
        long expiredDriverLicenses,
        long expiringDriverLicenses,
        long expiredDriverPdps,
        long expiringDriverPdps,
        long expiredRoadworthyCertificates,
        long expiringRoadworthyCertificates,
        long expiredInsurancePolicies,
        long expiringInsurancePolicies,
        long totalExpiredItems,
        long totalExpiringItems
) {
}