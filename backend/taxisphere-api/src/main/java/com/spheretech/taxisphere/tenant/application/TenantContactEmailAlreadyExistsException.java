package com.spheretech.taxisphere.tenant.application;

public class TenantContactEmailAlreadyExistsException extends RuntimeException {

    public TenantContactEmailAlreadyExistsException(String contactEmail) {
        super("Tenant contact email already exists: " + contactEmail);
    }
}
