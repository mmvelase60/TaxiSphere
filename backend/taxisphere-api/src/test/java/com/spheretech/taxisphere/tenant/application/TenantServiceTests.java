package com.spheretech.taxisphere.tenant.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.identity.domain.Role;
import com.spheretech.taxisphere.identity.domain.UserAccount;
import com.spheretech.taxisphere.identity.repository.RoleRepository;
import com.spheretech.taxisphere.identity.repository.UserAccountRepository;
import com.spheretech.taxisphere.shared.security.SecurityRoles;
import com.spheretech.taxisphere.tenant.api.CreateTenantRequest;
import com.spheretech.taxisphere.tenant.domain.Tenant;
import com.spheretech.taxisphere.tenant.persistence.TenantRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class TenantServiceTests {

    @Test
    void createsTenantAndFirstAssociationAdmin() {
        TenantRepository tenants = Mockito.mock(TenantRepository.class);
        UserAccountRepository users = Mockito.mock(UserAccountRepository.class);
        RoleRepository roles = Mockito.mock(RoleRepository.class);
        Role associationAdmin = new Role(UUID.randomUUID(), SecurityRoles.ASSOCIATION_ADMIN, "Association Administrator");

        when(roles.findByCode(SecurityRoles.ASSOCIATION_ADMIN)).thenReturn(Optional.of(associationAdmin));
        when(tenants.save(Mockito.any(Tenant.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(users.save(Mockito.any(UserAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TenantService service = new TenantService(
                tenants,
                users,
                roles,
                new BCryptPasswordEncoder()
        );

        TenantOnboardingResult result = service.createTenant(new CreateTenantRequest(
                "Pretoria Taxi Association",
                "office@pta-taxi.example",
                "admin@pta-taxi.example",
                "strong-local-password"
        ));

        ArgumentCaptor<UserAccount> userCaptor = ArgumentCaptor.forClass(UserAccount.class);
        Mockito.verify(users).save(userCaptor.capture());

        assertThat(result.tenant().getName()).isEqualTo("Pretoria Taxi Association");
        assertThat(result.adminUserId()).isEqualTo(userCaptor.getValue().getId());
        assertThat(userCaptor.getValue().getTenantId()).isEqualTo(result.tenant().getId());
        assertThat(userCaptor.getValue().getRoles()).containsExactly(associationAdmin);
    }

    @Test
    void rejectsDuplicateAdminEmail() {
        TenantRepository tenants = Mockito.mock(TenantRepository.class);
        UserAccountRepository users = Mockito.mock(UserAccountRepository.class);
        RoleRepository roles = Mockito.mock(RoleRepository.class);
        when(users.existsByEmailIgnoreCase("admin@pta-taxi.example")).thenReturn(true);

        TenantService service = new TenantService(
                tenants,
                users,
                roles,
                new BCryptPasswordEncoder()
        );

        assertThatThrownBy(() -> service.createTenant(new CreateTenantRequest(
                "Pretoria Taxi Association",
                "office@pta-taxi.example",
                "admin@pta-taxi.example",
                "strong-local-password"
        ))).isInstanceOf(TenantAdminEmailAlreadyExistsException.class);
    }
}
