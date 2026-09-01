CREATE TABLE passenger (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    association_id BINARY(16) NOT NULL,
    user_account_id BINARY(16) NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(40) NOT NULL,
    email VARCHAR(180) NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_passenger PRIMARY KEY (id),
    CONSTRAINT fk_passenger_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    CONSTRAINT fk_passenger_association FOREIGN KEY (association_id) REFERENCES taxi_association (id),
    CONSTRAINT fk_passenger_user_account FOREIGN KEY (user_account_id) REFERENCES user_account (id),
    CONSTRAINT uk_passenger_tenant_phone UNIQUE (tenant_id, phone_number),
    CONSTRAINT uk_passenger_tenant_email UNIQUE (tenant_id, email)
);

CREATE INDEX ix_passenger_tenant_status ON passenger (tenant_id, status);
CREATE INDEX ix_passenger_association ON passenger (association_id);
CREATE INDEX ix_passenger_user_account ON passenger (user_account_id);