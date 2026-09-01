package com.spheretech.taxisphere.shared.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JwtTokenServiceTests {

    @Test
    void createsAndParsesAccessToken() {
        JwtTokenService tokenService = new JwtTokenService(
                new JwtProperties("TaxiSphere", "replace-with-at-least-32-bytes-of-secret-key-material", 30)
        );
        AuthenticatedUser user = new AuthenticatedUser(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "platform-admin",
                List.of("PLATFORM_ADMIN")
        );

        String token = tokenService.createAccessToken(user);
        Claims claims = tokenService.parseClaims(token);

        assertThat(token).isNotBlank();
        assertThat(claims.getSubject()).isEqualTo("platform-admin");
        assertThat(claims.getIssuer()).isEqualTo("TaxiSphere");
        assertThat(claims.get("roles", List.class)).contains("PLATFORM_ADMIN");
    }
}
