package com.spheretech.taxisphere.platform.api;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/platform")
public class PlatformHealthController {

    private final String applicationName;
    private final String platformVersion;

    public PlatformHealthController(
            @Value("${taxisphere.platform.name}") String applicationName,
            @Value("${taxisphere.platform.version}") String platformVersion
    ) {
        this.applicationName = applicationName;
        this.platformVersion = platformVersion;
    }

    @GetMapping("/health")
    public PlatformHealthResponse health() {
        return new PlatformHealthResponse("UP", applicationName, platformVersion, Instant.now());
    }
}
