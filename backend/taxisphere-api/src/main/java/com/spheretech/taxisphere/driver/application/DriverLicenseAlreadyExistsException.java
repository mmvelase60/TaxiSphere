package com.spheretech.taxisphere.driver.application;

public class DriverLicenseAlreadyExistsException extends RuntimeException {

    public DriverLicenseAlreadyExistsException(String licenseNumber) {
        super("Driver license number already exists for this tenant: " + licenseNumber);
    }
}
