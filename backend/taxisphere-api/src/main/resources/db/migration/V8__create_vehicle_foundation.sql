CREATE TABLE vehicle (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    association_id BINARY(16) NOT NULL,
    registration_number VARCHAR(40) NOT NULL,
    make VARCHAR(80) NOT NULL,
    model VARCHAR(80) NOT NULL,
    model_year INT NOT NULL,
    seating_capacity INT NOT NULL,
    vin VARCHAR(80) NULL,
    roadworthy_expiry_date DATE NOT NULL,
    insurance_expiry_date DATE NOT NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_vehicle PRIMARY KEY (id),
    CONSTRAINT fk_vehicle_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    CONSTRAINT fk_vehicle_association FOREIGN KEY (association_id) REFERENCES taxi_association (id),
    CONSTRAINT uk_vehicle_tenant_registration UNIQUE (tenant_id, registration_number),
    CONSTRAINT uk_vehicle_tenant_vin UNIQUE (tenant_id, vin)
);

CREATE INDEX ix_vehicle_tenant_status ON vehicle (tenant_id, status);
CREATE INDEX ix_vehicle_association ON vehicle (association_id);
