package com.spheretech.taxisphere.shared.tenant;

public class TenantContextRequiredException extends RuntimeException {

    public TenantContextRequiredException() {
        super("Tenant context is required for this operation.");
    }
}
