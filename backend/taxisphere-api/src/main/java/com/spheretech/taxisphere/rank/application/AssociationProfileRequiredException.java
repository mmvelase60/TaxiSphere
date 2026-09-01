package com.spheretech.taxisphere.rank.application;

public class AssociationProfileRequiredException extends RuntimeException {

    public AssociationProfileRequiredException() {
        super("An association profile must exist before taxi ranks can be managed.");
    }
}
