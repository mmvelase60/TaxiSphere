package com.spheretech.taxisphere.tenant.application;

public class TenantAdminEmailAlreadyExistsException extends RuntimeException {

    public TenantAdminEmailAlreadyExistsException(String adminEmail) {
        super("Tenant administrator email already exists: " + adminEmail);
    }
}
