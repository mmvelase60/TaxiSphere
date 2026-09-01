package com.spheretech.taxisphere.association.application;

public class AssociationNotFoundException extends RuntimeException {

    public AssociationNotFoundException() {
        super("Association profile was not found for this tenant.");
    }
}
