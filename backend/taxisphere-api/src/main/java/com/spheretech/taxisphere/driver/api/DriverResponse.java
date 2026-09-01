package com.spheretech.taxisphere.driver.api;

import com.spheretech.taxisphere.driver.domain.Driver;
import com.spheretech.taxisphere.driver.domain.DriverStatus;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record DriverResponse(
        UUID id,
        UUID tenantId,
        UUID associationId,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String licenseNumber,
        String pdpNumber,
        LocalDate licenseExpiryDate,
        LocalDate pdpExpiryDate,
        DriverStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static DriverResponse from(Driver driver) {
        return new DriverResponse(
                driver.getId(),
                driver.getTenantId(),
                driver.getAssociationId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getPhoneNumber(),
                driver.getEmail(),
                driver.getLicenseNumber(),
                driver.getPdpNumber(),
                driver.getLicenseExpiryDate(),
                driver.getPdpExpiryDate(),
                driver.getStatus(),
                driver.getCreatedAt(),
                driver.getUpdatedAt()
        );
    }
}
