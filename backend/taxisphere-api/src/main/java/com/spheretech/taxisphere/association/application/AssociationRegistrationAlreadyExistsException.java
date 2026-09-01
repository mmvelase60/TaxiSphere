package com.spheretech.taxisphere.association.application;

public class AssociationRegistrationAlreadyExistsException extends RuntimeException {

    public AssociationRegistrationAlreadyExistsException(String registrationNumber) {
        super("Association registration number already exists: " + registrationNumber);
    }
}
