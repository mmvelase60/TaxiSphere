CREATE TABLE financial_transaction (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    association_id BINARY(16) NOT NULL,
    type VARCHAR(40) NOT NULL,
    category VARCHAR(60) NOT NULL,
    amount DECIMAL(12, 2) NOT NULL,
    business_date DATE NOT NULL,
    description VARCHAR(255) NULL,
    reference_type VARCHAR(80) NULL,
    reference_id BINARY(16) NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_financial_transaction PRIMARY KEY (id),
    CONSTRAINT fk_financial_transaction_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    CONSTRAINT fk_financial_transaction_association FOREIGN KEY (association_id) REFERENCES taxi_association (id)
);

CREATE INDEX ix_financial_transaction_tenant_date ON financial_transaction (tenant_id, business_date);
CREATE INDEX ix_financial_transaction_tenant_type ON financial_transaction (tenant_id, type);
CREATE INDEX ix_financial_transaction_association ON financial_transaction (association_id);