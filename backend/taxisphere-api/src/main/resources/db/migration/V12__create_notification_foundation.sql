CREATE TABLE notification_message (
    id BINARY(16) NOT NULL,
    tenant_id BINARY(16) NOT NULL,
    channel VARCHAR(40) NOT NULL,
    category VARCHAR(40) NOT NULL,
    recipient_address VARCHAR(180) NOT NULL,
    subject VARCHAR(160) NOT NULL,
    body VARCHAR(2000) NOT NULL,
    status VARCHAR(40) NOT NULL,
    failure_reason VARCHAR(500) NULL,
    sent_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_notification_message PRIMARY KEY (id),
    CONSTRAINT fk_notification_message_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id)
);

CREATE INDEX ix_notification_message_tenant_status ON notification_message (tenant_id, status);
CREATE INDEX ix_notification_message_tenant_created_at ON notification_message (tenant_id, created_at);