package com.spheretech.taxisphere.maintenance.application;

import java.util.UUID;

public class MaintenanceRecordNotFoundException extends RuntimeException {

    public MaintenanceRecordNotFoundException(UUID recordId) {
        super("Maintenance record not found: " + recordId);
    }
}