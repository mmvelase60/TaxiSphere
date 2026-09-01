CREATE TABLE taxi_rank (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    association_id BINARY(16) NOT NULL,
    name VARCHAR(160) NOT NULL,
    code VARCHAR(40) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(120) NOT NULL,
    province VARCHAR(120) NOT NULL,
    capacity INT NOT NULL,
    latitude DECIMAL(10, 7) NULL,
    longitude DECIMAL(10, 7) NULL,
    operating_hours VARCHAR(160) NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_taxi_rank PRIMARY KEY (id),
    CONSTRAINT fk_taxi_rank_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    CONSTRAINT fk_taxi_rank_association FOREIGN KEY (association_id) REFERENCES taxi_association (id),
    CONSTRAINT uk_taxi_rank_tenant_code UNIQUE (tenant_id, code)
);

CREATE INDEX ix_taxi_rank_tenant_status ON taxi_rank (tenant_id, status);
CREATE INDEX ix_taxi_rank_association ON taxi_rank (association_id);
