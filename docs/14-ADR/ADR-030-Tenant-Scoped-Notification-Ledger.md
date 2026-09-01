# ADR-030 — Create Tenant-Scoped Notification Ledger

## Status

Accepted

## Context

TaxiSphere now includes operational, reporting, and finance workflows that will eventually need outbound messages. The platform needs a safe foundation for recording messages before integrating external email, SMS, push, or in-app providers.

## Decision

Create a tenant-scoped notification ledger using a `NotificationMessage` entity. Each notification records tenant ID, channel, category, recipient address, subject, body, delivery status, optional failure reason, and optional sent timestamp.

## Consequences

- Notifications can be tracked before provider integration exists.
- Tenant isolation is enforced at the repository and service layer.
- Future asynchronous dispatch can process `PENDING` messages through RabbitMQ or another queue.
- Delivery failures can be recorded and surfaced in operations dashboards.