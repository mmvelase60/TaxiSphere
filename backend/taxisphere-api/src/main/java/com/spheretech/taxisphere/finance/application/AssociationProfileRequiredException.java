package com.spheretech.taxisphere.finance.application;

public class AssociationProfileRequiredException extends RuntimeException {

    public AssociationProfileRequiredException() {
        super("A tenant association profile is required before finance transactions can be recorded.");
    }
}