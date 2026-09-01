package com.spheretech.taxisphere.identity.service;

import com.spheretech.taxisphere.identity.api.LoginRequest;
import com.spheretech.taxisphere.identity.api.LoginResponse;
import com.spheretech.taxisphere.identity.domain.Role;
import com.spheretech.taxisphere.identity.domain.UserAccount;
import com.spheretech.taxisphere.identity.domain.UserStatus;
import com.spheretech.taxisphere.identity.repository.UserAccountRepository;
import com.spheretech.taxisphere.shared.security.jwt.AuthenticatedUser;
import com.spheretech.taxisphere.shared.security.jwt.JwtTokenService;
import java.time.Instant;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public AuthenticationService(
            UserAccountRepository userAccountRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService
    ) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        UserAccount user = userAccountRepository.findByEmailIgnoreCase(request.username())
                .filter(account -> account.getStatus() == UserStatus.ACTIVE)
                .filter(account -> passwordEncoder.matches(request.password(), account.getPasswordHash()))
                .orElseThrow(InvalidCredentialsException::new);

        List<String> roles = user.getRoles().stream()
                .map(Role::getCode)
                .sorted()
                .toList();

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                user.getId(),
                user.getTenantId(),
                user.getEmail(),
                roles
        );

        Instant expiresAt = jwtTokenService.accessTokenExpiresAt();
        String token = jwtTokenService.createAccessToken(authenticatedUser, expiresAt);

        return new LoginResponse("Bearer", token, expiresAt);
    }
}
