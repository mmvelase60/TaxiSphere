package com.spheretech.taxisphere.driver.application;

public class AssociationProfileRequiredException extends RuntimeException {

    public AssociationProfileRequiredException() {
        super("An association profile must exist before drivers can be managed.");
    }
}
