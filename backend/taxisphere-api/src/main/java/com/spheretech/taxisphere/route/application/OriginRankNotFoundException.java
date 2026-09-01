package com.spheretech.taxisphere.route.application;

import java.util.UUID;

public class OriginRankNotFoundException extends RuntimeException {

    public OriginRankNotFoundException(UUID rankId) {
        super("Origin rank was not found for this tenant: " + rankId);
    }
}
