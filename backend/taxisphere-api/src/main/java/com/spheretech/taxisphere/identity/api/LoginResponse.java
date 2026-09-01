package com.spheretech.taxisphere.identity.api;

import java.time.Instant;

public record LoginResponse(
        String tokenType,
        String accessToken,
        Instant expiresAt
) {
}
