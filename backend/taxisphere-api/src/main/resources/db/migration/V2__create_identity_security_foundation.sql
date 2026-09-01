CREATE TABLE user_account (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NULL,
    email VARCHAR(180) NOT NULL,
    password_hash VARCHAR(120) NOT NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_user_account PRIMARY KEY (id),
    CONSTRAINT uk_user_account_email UNIQUE (email),
    CONSTRAINT fk_user_account_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id)
);

CREATE TABLE security_role (
    id BINARY(16) NOT NULL,
    code VARCHAR(80) NOT NULL,
    name VARCHAR(160) NOT NULL,
    CONSTRAINT pk_security_role PRIMARY KEY (id),
    CONSTRAINT uk_security_role_code UNIQUE (code)
);

CREATE TABLE user_account_role (
    user_account_id BINARY(16) NOT NULL,
    role_id BINARY(16) NOT NULL,
    CONSTRAINT pk_user_account_role PRIMARY KEY (user_account_id, role_id),
    CONSTRAINT fk_user_account_role_user FOREIGN KEY (user_account_id) REFERENCES user_account (id),
    CONSTRAINT fk_user_account_role_role FOREIGN KEY (role_id) REFERENCES security_role (id)
);

CREATE INDEX ix_user_account_tenant_status ON user_account (tenant_id, status);
