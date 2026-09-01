package com.spheretech.taxisphere.identity.bootstrap;

import static org.assertj.core.api.Assertions.assertThat;

import com.spheretech.taxisphere.identity.domain.Role;
import com.spheretech.taxisphere.identity.domain.UserAccount;
import com.spheretech.taxisphere.identity.repository.RoleRepository;
import com.spheretech.taxisphere.identity.repository.UserAccountRepository;
import com.spheretech.taxisphere.shared.security.SecurityRoles;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class IdentityBootstrapRunnerTests {

    @Test
    void createsPlatformAdminOnlyWhenEnabledAndConfigured() {
        UserAccountRepository users = Mockito.mock(UserAccountRepository.class);
        RoleRepository roles = Mockito.mock(RoleRepository.class);
        Mockito.when(users.findByEmailIgnoreCase("admin@taxisphere.local")).thenReturn(Optional.empty());
        Mockito.when(roles.findByCode(SecurityRoles.PLATFORM_ADMIN)).thenReturn(Optional.of(
                new Role(UUID.randomUUID(), SecurityRoles.PLATFORM_ADMIN, "Platform Administrator")
        ));

        IdentityBootstrapRunner runner = new IdentityBootstrapRunner(
                new IdentityBootstrapProperties(true, "admin@taxisphere.local", "not-a-default-secret"),
                users,
                roles,
                new BCryptPasswordEncoder()
        );

        runner.run();

        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
        Mockito.verify(users).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("admin@taxisphere.local");
        assertThat(captor.getValue().getRoles()).hasSize(1);
    }

    @Test
    void doesNothingWhenDisabled() {
        UserAccountRepository users = Mockito.mock(UserAccountRepository.class);
        RoleRepository roles = Mockito.mock(RoleRepository.class);

        IdentityBootstrapRunner runner = new IdentityBootstrapRunner(
                new IdentityBootstrapProperties(false, "", ""),
                users,
                roles,
                new BCryptPasswordEncoder()
        );

        runner.run();

        Mockito.verifyNoInteractions(users, roles);
    }
}
