package com.spheretech.taxisphere.tenant.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spheretech.taxisphere.tenant.api.CreateTenantRequest;
import com.spheretech.taxisphere.tenant.domain.Tenant;
import com.spheretech.taxisphere.tenant.domain.TenantStatus;
import com.spheretech.taxisphere.tenant.persistence.TenantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TenantService.class)
class TenantServiceTests {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TenantRepository tenantRepository;

    @Test
    void createsTenantInSetupStatus() {
        Tenant tenant = tenantService.createTenant(new CreateTenantRequest("Pretoria Taxi Association", "ops@pta.example"));

        assertThat(tenant.getId()).isNotNull();
        assertThat(tenant.getStatus()).isEqualTo(TenantStatus.SETUP);
        assertThat(tenantRepository.count()).isEqualTo(1);
    }

    @Test
    void rejectsDuplicateTenantName() {
        CreateTenantRequest request = new CreateTenantRequest("Pretoria Taxi Association", "ops@pta.example");
        tenantService.createTenant(request);

        assertThatThrownBy(() -> tenantService.createTenant(request))
                .isInstanceOf(TenantAlreadyExistsException.class);
    }
}
