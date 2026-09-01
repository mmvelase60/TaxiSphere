package com.spheretech.taxisphere.identity.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.spheretech.taxisphere.identity.api.LoginRequest;
import com.spheretech.taxisphere.identity.api.LoginResponse;
import com.spheretech.taxisphere.identity.domain.Role;
import com.spheretech.taxisphere.identity.domain.UserAccount;
import com.spheretech.taxisphere.identity.domain.UserStatus;
import com.spheretech.taxisphere.identity.repository.UserAccountRepository;
import com.spheretech.taxisphere.shared.security.SecurityRoles;
import com.spheretech.taxisphere.shared.security.jwt.JwtProperties;
import com.spheretech.taxisphere.shared.security.jwt.JwtTokenService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthenticationServiceTests {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtTokenService jwtTokenService = new JwtTokenService(
            new JwtProperties("TaxiSphere", "replace-with-at-least-32-bytes-of-secret-key-material", 30)
    );

    @Test
    void returnsBearerTokenForActiveUserWithValidCredentials() {
        UserAccountRepository repository = org.mockito.Mockito.mock(UserAccountRepository.class);
        UserAccount user = new UserAccount(
                UUID.randomUUID(),
                null,
                "admin@taxisphere.local",
                passwordEncoder.encode("password"),
                UserStatus.ACTIVE
        );
        user.assignRole(new Role(UUID.randomUUID(), SecurityRoles.PLATFORM_ADMIN, "Platform Administrator"));
        when(repository.findByEmailIgnoreCase("admin@taxisphere.local")).thenReturn(Optional.of(user));

        AuthenticationService service = new AuthenticationService(repository, passwordEncoder, jwtTokenService);
        LoginResponse response = service.login(new LoginRequest("admin@taxisphere.local", "password"));

        assertThat(response.tokenType()).isEqualTo("Bearer");
        assertThat(response.accessToken()).isNotBlank();
        assertThat(response.expiresAt()).isNotNull();
    }

    @Test
    void rejectsInvalidCredentials() {
        UserAccountRepository repository = org.mockito.Mockito.mock(UserAccountRepository.class);
        when(repository.findByEmailIgnoreCase("missing@taxisphere.local")).thenReturn(Optional.empty());

        AuthenticationService service = new AuthenticationService(repository, passwordEncoder, jwtTokenService);

        assertThatThrownBy(() -> service.login(new LoginRequest("missing@taxisphere.local", "wrong")))
                .isInstanceOf(InvalidCredentialsException.class);
    }
}
