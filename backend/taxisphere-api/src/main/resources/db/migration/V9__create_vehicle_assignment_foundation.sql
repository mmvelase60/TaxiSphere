CREATE TABLE vehicle_assignment (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    association_id BINARY(16) NOT NULL,
    driver_id BINARY(16) NOT NULL,
    vehicle_id BINARY(16) NOT NULL,
    assigned_date DATE NOT NULL,
    ended_date DATE NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_vehicle_assignment PRIMARY KEY (id),
    CONSTRAINT fk_vehicle_assignment_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    CONSTRAINT fk_vehicle_assignment_association FOREIGN KEY (association_id) REFERENCES taxi_association (id),
    CONSTRAINT fk_vehicle_assignment_driver FOREIGN KEY (driver_id) REFERENCES driver (id),
    CONSTRAINT fk_vehicle_assignment_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle (id)
);

CREATE INDEX ix_vehicle_assignment_tenant_status ON vehicle_assignment (tenant_id, status);
CREATE INDEX ix_vehicle_assignment_driver_status ON vehicle_assignment (driver_id, status);
CREATE INDEX ix_vehicle_assignment_vehicle_status ON vehicle_assignment (vehicle_id, status);
