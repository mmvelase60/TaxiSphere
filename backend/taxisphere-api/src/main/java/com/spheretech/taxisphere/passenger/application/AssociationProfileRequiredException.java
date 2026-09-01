package com.spheretech.taxisphere.passenger.application;

public class AssociationProfileRequiredException extends RuntimeException {

    public AssociationProfileRequiredException() {
        super("A tenant association profile is required before passengers can be created.");
    }
}