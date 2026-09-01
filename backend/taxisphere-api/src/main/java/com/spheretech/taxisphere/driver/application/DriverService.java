package com.spheretech.taxisphere.driver.application;

import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.driver.api.CreateDriverRequest;
import com.spheretech.taxisphere.driver.domain.Driver;
import com.spheretech.taxisphere.driver.domain.DriverStatus;
import com.spheretech.taxisphere.driver.persistence.DriverRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final TaxiAssociationRepository associationRepository;

    public DriverService(
            DriverRepository driverRepository,
            TaxiAssociationRepository associationRepository
    ) {
        this.driverRepository = driverRepository;
        this.associationRepository = associationRepository;
    }

    @Transactional(readOnly = true)
    public List<Driver> findAllForCurrentTenant() {
        return driverRepository.findAllByTenantIdOrderByLastNameAscFirstNameAsc(currentTenantId());
    }

    @Transactional(readOnly = true)
    public Driver findByIdForCurrentTenant(UUID driverId) {
        UUID tenantId = currentTenantId();
        return driverRepository.findByIdAndTenantId(driverId, tenantId)
                .orElseThrow(() -> new DriverNotFoundException(driverId));
    }

    @Transactional
    public Driver createDriver(CreateDriverRequest request) {
        UUID tenantId = currentTenantId();

        if (driverRepository.existsByTenantIdAndLicenseNumberIgnoreCase(tenantId, request.licenseNumber())) {
            throw new DriverLicenseAlreadyExistsException(request.licenseNumber());
        }

        if (driverRepository.existsByTenantIdAndPdpNumberIgnoreCase(tenantId, request.pdpNumber())) {
            throw new DriverPdpAlreadyExistsException(request.pdpNumber());
        }

        TaxiAssociation association = associationRepository.findByTenantId(tenantId)
                .orElseThrow(AssociationProfileRequiredException::new);

        Driver driver = new Driver(
                UUID.randomUUID(),
                tenantId,
                association.getId(),
                request.firstName(),
                request.lastName(),
                request.phoneNumber(),
                request.email(),
                request.licenseNumber(),
                request.pdpNumber(),
                request.licenseExpiryDate(),
                request.pdpExpiryDate(),
                DriverStatus.PENDING_VERIFICATION
        );

        return driverRepository.save(driver);
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}
