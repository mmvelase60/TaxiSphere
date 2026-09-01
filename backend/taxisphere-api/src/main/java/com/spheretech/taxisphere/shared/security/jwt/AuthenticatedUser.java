package com.spheretech.taxisphere.shared.security.jwt;

import java.util.List;
import java.util.UUID;

public record AuthenticatedUser(
        UUID userId,
        UUID tenantId,
        String username,
        List<String> roles
) {
}
