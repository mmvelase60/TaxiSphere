package com.spheretech.taxisphere.shared.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.spheretech.taxisphere.shared.security.SecurityRoles;
import com.spheretech.taxisphere.shared.tenant.TenantContextHolder;
import jakarta.servlet.FilterChain;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

class JwtAuthenticationFilterTests {

    private final JwtTokenService jwtTokenService = new JwtTokenService(
            new JwtProperties("TaxiSphere", "replace-with-at-least-32-bytes-of-secret-key-material", 30)
    );

    @Test
    void authenticatesBearerTokenAndClearsContextAfterRequest() throws Exception {
        UUID tenantId = UUID.randomUUID();
        String token = jwtTokenService.createAccessToken(new AuthenticatedUser(
                UUID.randomUUID(),
                tenantId,
                "admin@taxisphere.local",
                List.of(SecurityRoles.PLATFORM_ADMIN)
        ));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean filterChainReached = new AtomicBoolean(false);

        FilterChain filterChain = (servletRequest, servletResponse) -> {
            filterChainReached.set(true);
            assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
            assertThat(SecurityContextHolder.getContext().getAuthentication().getAuthorities())
                    .extracting("authority")
                    .contains("ROLE_PLATFORM_ADMIN");
            assertThat(TenantContextHolder.current()).isPresent();
            assertThat(TenantContextHolder.current().orElseThrow().tenantId()).isEqualTo(tenantId);
        };

        new JwtAuthenticationFilter(jwtTokenService).doFilter(request, response, filterChain);

        assertThat(filterChainReached).isTrue();
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        assertThat(TenantContextHolder.current()).isEmpty();
    }

    @Test
    void rejectsInvalidBearerToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer invalid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        new JwtAuthenticationFilter(jwtTokenService).doFilter(
                request,
                response,
                (servletRequest, servletResponse) -> {
                    throw new AssertionError("Invalid tokens must not continue through the filter chain.");
                }
        );

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.getContentAsString()).contains("invalid-token");
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        assertThat(TenantContextHolder.current()).isEmpty();
    }
}
