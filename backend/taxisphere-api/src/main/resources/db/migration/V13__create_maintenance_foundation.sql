CREATE TABLE maintenance_record (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    association_id BINARY(16) NOT NULL,
    vehicle_id BINARY(16) NOT NULL,
    type VARCHAR(40) NOT NULL,
    status VARCHAR(40) NOT NULL,
    scheduled_date DATE NOT NULL,
    completed_date DATE NULL,
    cost DECIMAL(12, 2) NOT NULL,
    service_provider VARCHAR(160) NULL,
    description VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_maintenance_record PRIMARY KEY (id),
    CONSTRAINT fk_maintenance_record_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    CONSTRAINT fk_maintenance_record_association FOREIGN KEY (association_id) REFERENCES taxi_association (id),
    CONSTRAINT fk_maintenance_record_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle (id)
);

CREATE INDEX ix_maintenance_record_tenant_status ON maintenance_record (tenant_id, status);
CREATE INDEX ix_maintenance_record_tenant_scheduled_date ON maintenance_record (tenant_id, scheduled_date);
CREATE INDEX ix_maintenance_record_vehicle ON maintenance_record (vehicle_id);