CREATE TABLE taxi_association (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    name VARCHAR(160) NOT NULL,
    registration_number VARCHAR(80) NULL,
    contact_email VARCHAR(180) NOT NULL,
    contact_phone VARCHAR(40) NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_taxi_association PRIMARY KEY (id),
    CONSTRAINT fk_taxi_association_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    CONSTRAINT uk_taxi_association_tenant UNIQUE (tenant_id),
    CONSTRAINT uk_taxi_association_registration UNIQUE (registration_number)
);

CREATE INDEX ix_taxi_association_tenant_status ON taxi_association (tenant_id, status);
