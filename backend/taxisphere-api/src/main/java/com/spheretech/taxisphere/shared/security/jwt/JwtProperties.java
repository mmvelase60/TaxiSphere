package com.spheretech.taxisphere.shared.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "taxisphere.security.jwt")
public record JwtProperties(
        String issuer,
        String secret,
        long accessTokenMinutes
) {
}
