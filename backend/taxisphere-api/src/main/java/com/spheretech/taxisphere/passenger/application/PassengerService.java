package com.spheretech.taxisphere.passenger.application;

import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.passenger.api.CreatePassengerRequest;
import com.spheretech.taxisphere.passenger.domain.Passenger;
import com.spheretech.taxisphere.passenger.domain.PassengerStatus;
import com.spheretech.taxisphere.passenger.persistence.PassengerRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final TaxiAssociationRepository associationRepository;

    public PassengerService(
            PassengerRepository passengerRepository,
            TaxiAssociationRepository associationRepository
    ) {
        this.passengerRepository = passengerRepository;
        this.associationRepository = associationRepository;
    }

    @Transactional(readOnly = true)
    public List<Passenger> findAllForCurrentTenant() {
        return passengerRepository.findAllByTenantIdOrderByLastNameAscFirstNameAsc(currentTenantId());
    }

    @Transactional(readOnly = true)
    public Passenger findByIdForCurrentTenant(UUID passengerId) {
        UUID tenantId = currentTenantId();
        return passengerRepository.findByIdAndTenantId(passengerId, tenantId)
                .orElseThrow(() -> new PassengerNotFoundException(passengerId));
    }

    @Transactional
    public Passenger createPassenger(CreatePassengerRequest request) {
        UUID tenantId = currentTenantId();

        if (passengerRepository.existsByTenantIdAndPhoneNumberIgnoreCase(tenantId, request.phoneNumber())) {
            throw new PassengerPhoneAlreadyExistsException(request.phoneNumber());
        }

        if (StringUtils.hasText(request.email())
                && passengerRepository.existsByTenantIdAndEmailIgnoreCase(tenantId, request.email())) {
            throw new PassengerEmailAlreadyExistsException(request.email());
        }

        TaxiAssociation association = associationRepository.findByTenantId(tenantId)
                .orElseThrow(AssociationProfileRequiredException::new);

        Passenger passenger = new Passenger(
                UUID.randomUUID(),
                tenantId,
                association.getId(),
                request.userAccountId(),
                request.firstName(),
                request.lastName(),
                request.phoneNumber(),
                request.email(),
                PassengerStatus.ACTIVE
        );

        return passengerRepository.save(passenger);
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}