CREATE TABLE tenant (
    id BINARY(16) NOT NULL,
    name VARCHAR(160) NOT NULL,
    contact_email VARCHAR(180) NOT NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_tenant PRIMARY KEY (id),
    CONSTRAINT uk_tenant_name UNIQUE (name)
);

CREATE TABLE audit_log (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NULL,
    actor_user_id BINARY(16) NULL,
    action VARCHAR(120) NOT NULL,
    resource_type VARCHAR(120) NOT NULL,
    resource_id VARCHAR(120) NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_audit_log PRIMARY KEY (id),
    CONSTRAINT fk_audit_log_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id)
);

CREATE INDEX ix_audit_log_tenant_created_at ON audit_log (tenant_id, created_at);
