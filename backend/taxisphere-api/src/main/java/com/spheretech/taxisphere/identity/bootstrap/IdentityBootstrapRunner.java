package com.spheretech.taxisphere.identity.bootstrap;

import com.spheretech.taxisphere.identity.domain.Role;
import com.spheretech.taxisphere.identity.domain.UserAccount;
import com.spheretech.taxisphere.identity.domain.UserStatus;
import com.spheretech.taxisphere.identity.repository.RoleRepository;
import com.spheretech.taxisphere.identity.repository.UserAccountRepository;
import com.spheretech.taxisphere.shared.security.SecurityRoles;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@EnableConfigurationProperties(IdentityBootstrapProperties.class)
public class IdentityBootstrapRunner implements CommandLineRunner {

    private final IdentityBootstrapProperties properties;
    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public IdentityBootstrapRunner(
            IdentityBootstrapProperties properties,
            UserAccountRepository userAccountRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.properties = properties;
        this.userAccountRepository = userAccountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!properties.enabled()) {
            return;
        }

        if (!StringUtils.hasText(properties.email()) || !StringUtils.hasText(properties.password())) {
            throw new IllegalStateException("Identity bootstrap requires both email and password when enabled.");
        }

        userAccountRepository.findByEmailIgnoreCase(properties.email()).ifPresentOrElse(
                existing -> {
                },
                this::createBootstrapAdmin
        );
    }

    private void createBootstrapAdmin() {
        Role platformAdminRole = roleRepository.findByCode(SecurityRoles.PLATFORM_ADMIN)
                .orElseThrow(() -> new IllegalStateException("Missing PLATFORM_ADMIN role seed."));

        UserAccount user = new UserAccount(
                UUID.randomUUID(),
                null,
                properties.email(),
                passwordEncoder.encode(properties.password()),
                UserStatus.ACTIVE
        );
        user.assignRole(platformAdminRole);

        userAccountRepository.save(user);
    }
}
