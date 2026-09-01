package com.spheretech.taxisphere.association.application;

import com.spheretech.taxisphere.association.api.CreateAssociationRequest;
import com.spheretech.taxisphere.association.domain.AssociationStatus;
import com.spheretech.taxisphere.association.domain.TaxiAssociation;
import com.spheretech.taxisphere.association.persistence.TaxiAssociationRepository;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import com.spheretech.taxisphere.shared.tenant.TenantContextRequiredException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AssociationService {

    private final TaxiAssociationRepository associationRepository;

    public AssociationService(TaxiAssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    @Transactional(readOnly = true)
    public TaxiAssociation getCurrentTenantAssociation() {
        UUID tenantId = currentTenantId();
        return associationRepository.findByTenantId(tenantId)
                .orElseThrow(AssociationNotFoundException::new);
    }

    @Transactional
    public TaxiAssociation createAssociation(CreateAssociationRequest request) {
        UUID tenantId = currentTenantId();

        if (associationRepository.existsByTenantId(tenantId)) {
            throw new AssociationAlreadyExistsException();
        }

        if (StringUtils.hasText(request.registrationNumber())
                && associationRepository.existsByRegistrationNumberIgnoreCase(request.registrationNumber())) {
            throw new AssociationRegistrationAlreadyExistsException(request.registrationNumber());
        }

        TaxiAssociation association = new TaxiAssociation(
                UUID.randomUUID(),
                tenantId,
                request.name(),
                request.registrationNumber(),
                request.contactEmail(),
                request.contactPhone(),
                AssociationStatus.SETUP
        );

        return associationRepository.save(association);
    }

    private UUID currentTenantId() {
        return TenantContextHolder.current()
                .orElseThrow(TenantContextRequiredException::new)
                .tenantId();
    }
}
