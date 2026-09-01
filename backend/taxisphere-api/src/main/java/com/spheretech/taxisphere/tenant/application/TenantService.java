package com.spheretech.taxisphere.tenant.application;

import com.spheretech.taxisphere.identity.domain.Role;
import com.spheretech.taxisphere.identity.domain.UserAccount;
import com.spheretech.taxisphere.identity.domain.UserStatus;
import com.spheretech.taxisphere.identity.repository.RoleRepository;
import com.spheretech.taxisphere.identity.repository.UserAccountRepository;
import com.spheretech.taxisphere.shared.security.SecurityRoles;
import com.spheretech.taxisphere.tenant.api.CreateTenantRequest;
import com.spheretech.taxisphere.tenant.domain.Tenant;
import com.spheretech.taxisphere.tenant.domain.TenantStatus;
import com.spheretech.taxisphere.tenant.persistence.TenantRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public TenantService(
            TenantRepository tenantRepository,
            UserAccountRepository userAccountRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.tenantRepository = tenantRepository;
        this.userAccountRepository = userAccountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<Tenant> findAll() {
        return tenantRepository.findAll();
    }

    @Transactional
    public TenantOnboardingResult createTenant(CreateTenantRequest request) {
        validateTenantRequest(request);

        Tenant tenant = new Tenant(UUID.randomUUID(), request.name(), request.contactEmail(), TenantStatus.SETUP);
        Tenant savedTenant = tenantRepository.save(tenant);

        Role associationAdminRole = roleRepository.findByCode(SecurityRoles.ASSOCIATION_ADMIN)
                .orElseThrow(() -> new IllegalStateException("Missing ASSOCIATION_ADMIN role seed."));

        UserAccount adminUser = new UserAccount(
                UUID.randomUUID(),
                savedTenant.getId(),
                request.adminEmail(),
                passwordEncoder.encode(request.adminPassword()),
                UserStatus.ACTIVE
        );
        adminUser.assignRole(associationAdminRole);

        UserAccount savedAdmin = userAccountRepository.save(adminUser);
        return new TenantOnboardingResult(savedTenant, savedAdmin.getId());
    }

    private void validateTenantRequest(CreateTenantRequest request) {
        if (tenantRepository.existsByNameIgnoreCase(request.name())) {
            throw new TenantAlreadyExistsException(request.name());
        }

        if (tenantRepository.existsByContactEmailIgnoreCase(request.contactEmail())) {
            throw new TenantContactEmailAlreadyExistsException(request.contactEmail());
        }

        if (userAccountRepository.existsByEmailIgnoreCase(request.adminEmail())) {
            throw new TenantAdminEmailAlreadyExistsException(request.adminEmail());
        }
    }
}
