# ADR-022 — Model Drivers as Tenant-Owned Compliance Records

## Status

Accepted

## Context

Drivers are core operational actors in TaxiSphere. Driver records must support compliance tracking before vehicles, assignments, dispatch, and trips can be implemented.

## Decision

Create a tenant-owned `driver` table linked to `taxi_association`. Store licence and PDP identifiers with expiry dates and enforce uniqueness within each tenant.

## Consequences

- Drivers are isolated per tenant.
- Compliance expiry tracking is available early.
- Vehicle assignment and trip dispatch modules can build on a stable driver model.
- Future releases can add document uploads, verification workflows, and performance metrics.
