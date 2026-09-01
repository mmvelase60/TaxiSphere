CREATE TABLE driver (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    association_id BINARY(16) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(40) NOT NULL,
    email VARCHAR(180) NULL,
    license_number VARCHAR(80) NOT NULL,
    pdp_number VARCHAR(80) NOT NULL,
    license_expiry_date DATE NOT NULL,
    pdp_expiry_date DATE NOT NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_driver PRIMARY KEY (id),
    CONSTRAINT fk_driver_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    CONSTRAINT fk_driver_association FOREIGN KEY (association_id) REFERENCES taxi_association (id),
    CONSTRAINT uk_driver_tenant_license UNIQUE (tenant_id, license_number),
    CONSTRAINT uk_driver_tenant_pdp UNIQUE (tenant_id, pdp_number)
);

CREATE INDEX ix_driver_tenant_status ON driver (tenant_id, status);
CREATE INDEX ix_driver_association ON driver (association_id);
