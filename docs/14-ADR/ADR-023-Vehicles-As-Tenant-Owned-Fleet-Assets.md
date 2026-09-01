# ADR-023 — Model Vehicles as Tenant-Owned Fleet Assets

## Status

Accepted

## Context

Vehicles are core operational assets in TaxiSphere. The platform needs vehicle records before implementing assignments, dispatch, trips, maintenance, and compliance reporting.

## Decision

Create a tenant-owned `vehicle` table linked to `taxi_association`. Store registration, fleet identity, capacity, roadworthy expiry, insurance expiry, and operational status.

## Consequences

- Vehicles are isolated per tenant.
- Fleet compliance tracking starts early.
- Driver assignment and trip dispatch modules can build on a stable vehicle model.
- Future releases can add maintenance logs, inspection documents, ownership records, and performance metrics.
