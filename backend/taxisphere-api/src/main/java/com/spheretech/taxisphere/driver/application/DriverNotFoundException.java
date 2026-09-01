package com.spheretech.taxisphere.driver.application;

import java.util.UUID;

public class DriverNotFoundException extends RuntimeException {

    public DriverNotFoundException(UUID driverId) {
        super("Driver was not found for this tenant: " + driverId);
    }
}
