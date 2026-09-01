package com.spheretech.taxisphere.tenant.application;

public class TenantAlreadyExistsException extends RuntimeException {

    public TenantAlreadyExistsException(String tenantName) {
        super("Tenant already exists: " + tenantName);
    }
}
