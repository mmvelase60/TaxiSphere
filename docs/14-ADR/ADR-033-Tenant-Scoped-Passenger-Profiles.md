# ADR-033 — Create Tenant-Scoped Passenger Profiles

## Status

Accepted

## Context

TaxiSphere has operations, reporting, finance, notifications, compliance, and maintenance foundations. Passenger-facing capabilities such as tickets, mobile apps, payments, and notifications require a tenant-owned passenger profile model.

## Decision

Create a `Passenger` model owned by tenant and association. Passenger profiles store optional user account linkage, name, phone number, optional email, and lifecycle status.

## Consequences

- Passenger data can be managed before mobile login is introduced.
- Future ticketing and payment features can reference passenger profiles.
- Passenger profiles remain isolated by tenant.
- User account linkage remains optional, avoiding premature coupling to authentication flows.