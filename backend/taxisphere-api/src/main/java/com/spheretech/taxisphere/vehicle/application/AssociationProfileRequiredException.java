package com.spheretech.taxisphere.vehicle.application;

public class AssociationProfileRequiredException extends RuntimeException {

    public AssociationProfileRequiredException() {
        super("An association profile must exist before vehicles can be managed.");
    }
}
