CREATE TABLE taxi_route (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    association_id BINARY(16) NOT NULL,
    origin_rank_id BINARY(16) NULL,
    code VARCHAR(40) NOT NULL,
    origin VARCHAR(160) NOT NULL,
    destination VARCHAR(160) NOT NULL,
    fare DECIMAL(10, 2) NOT NULL,
    distance_km DECIMAL(8, 2) NOT NULL,
    estimated_minutes INT NOT NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_taxi_route PRIMARY KEY (id),
    CONSTRAINT fk_taxi_route_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    CONSTRAINT fk_taxi_route_association FOREIGN KEY (association_id) REFERENCES taxi_association (id),
    CONSTRAINT fk_taxi_route_origin_rank FOREIGN KEY (origin_rank_id) REFERENCES taxi_rank (id),
    CONSTRAINT uk_taxi_route_tenant_code UNIQUE (tenant_id, code)
);

CREATE INDEX ix_taxi_route_tenant_status ON taxi_route (tenant_id, status);
CREATE INDEX ix_taxi_route_association ON taxi_route (association_id);
CREATE INDEX ix_taxi_route_origin_rank ON taxi_route (origin_rank_id);
