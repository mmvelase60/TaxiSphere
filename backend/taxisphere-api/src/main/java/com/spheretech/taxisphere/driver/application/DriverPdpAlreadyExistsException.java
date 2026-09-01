package com.spheretech.taxisphere.driver.application;

public class DriverPdpAlreadyExistsException extends RuntimeException {

    public DriverPdpAlreadyExistsException(String pdpNumber) {
        super("Driver PDP number already exists for this tenant: " + pdpNumber);
    }
}
