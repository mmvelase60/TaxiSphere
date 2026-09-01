package com.spheretech.taxisphere.association.application;

public class AssociationAlreadyExistsException extends RuntimeException {

    public AssociationAlreadyExistsException() {
        super("An association profile already exists for this tenant.");
    }
}
