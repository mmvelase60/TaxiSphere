package com.spheretech.taxisphere.platform.api;

import java.time.Instant;

public record PlatformHealthResponse(
        String status,
        String application,
        String version,
        Instant timestamp
) {
}
