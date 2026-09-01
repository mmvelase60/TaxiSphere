package com.spheretech.taxisphere.shared.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "taxisphere.security.bootstrap-admin")
public record BootstrapAdminProperties(
        String username,
        String password
) {
}
