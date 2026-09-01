package com.spheretech.taxisphere.vehicle.application;

import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import com.spheretech.taxisphere.vehicle.api.CreateVehicleRequest;
import com.spheretech.taxisphere.vehicle.domain.Vehicle;
import com.spheretech.taxisphere.vehicle.domain.VehicleStatus;
import com.spheretech.taxisphere.vehicle.persistence.VehicleRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final TaxiAssociationRepository associationRepository;

    public VehicleService(
            VehicleRepository vehicleRepository,
            TaxiAssociationRepository associationRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.associationRepository = associationRepository;
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findAllForCurrentTenant() {
        return vehicleRepository.findAllByTenantIdOrderByRegistrationNumberAsc(currentTenantId());
    }

    @Transactional(readOnly = true)
    public Vehicle findByIdForCurrentTenant(UUID vehicleId) {
        UUID tenantId = currentTenantId();
        return vehicleRepository.findByIdAndTenantId(vehicleId, tenantId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));
    }

    @Transactional
    public Vehicle createVehicle(CreateVehicleRequest request) {
        UUID tenantId = currentTenantId();

        if (vehicleRepository.existsByTenantIdAndRegistrationNumberIgnoreCase(tenantId, request.registrationNumber())) {
            throw new VehicleRegistrationAlreadyExistsException(request.registrationNumber());
        }

        if (StringUtils.hasText(request.vin())
                && vehicleRepository.existsByTenantIdAndVinIgnoreCase(tenantId, request.vin())) {
            throw new VehicleVinAlreadyExistsException(request.vin());
        }

        TaxiAssociation association = associationRepository.findByTenantId(tenantId)
                .orElseThrow(AssociationProfileRequiredException::new);

        Vehicle vehicle = new Vehicle(
                UUID.randomUUID(),
                tenantId,
                association.getId(),
                request.registrationNumber(),
                request.make(),
                request.model(),
                request.modelYear(),
                request.seatingCapacity(),
                request.vin(),
                request.roadworthyExpiryDate(),
                request.insuranceExpiryDate(),
                VehicleStatus.PENDING_VERIFICATION
        );

        return vehicleRepository.save(vehicle);
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}
