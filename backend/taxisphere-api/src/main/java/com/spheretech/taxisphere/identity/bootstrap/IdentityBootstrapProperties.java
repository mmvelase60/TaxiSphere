package com.spheretech.taxisphere.identity.bootstrap;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "taxisphere.identity.bootstrap")
public record IdentityBootstrapProperties(
        boolean enabled,
        String email,
        String password
) {
}
