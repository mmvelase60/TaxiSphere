package com.spheretech.taxisphere.tenant.application;

import com.spheretech.taxisphere.tenant.api.CreateTenantRequest;
import com.spheretech.taxisphere.tenant.domain.Tenant;
import com.spheretech.taxisphere.tenant.domain.TenantStatus;
import com.spheretech.taxisphere.tenant.persistence.TenantRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Transactional(readOnly = true)
    public List<Tenant> findAll() {
        return tenantRepository.findAll();
    }

    @Transactional
    public Tenant createTenant(CreateTenantRequest request) {
        if (tenantRepository.existsByNameIgnoreCase(request.name())) {
            throw new TenantAlreadyExistsException(request.name());
        }

        Tenant tenant = new Tenant(UUID.randomUUID(), request.name(), request.contactEmail(), TenantStatus.SETUP);
        return tenantRepository.save(tenant);
    }
}
