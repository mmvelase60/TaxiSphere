# ADR-021 — Model Routes as Tenant-Owned Fare Definitions

## Status

Accepted

## Context

TaxiSphere needs route and fare management before dispatch and trip modules can be built.

## Decision

Create a tenant-owned `taxi_route` table linked to the association and optionally linked to an origin taxi rank. Route APIs resolve tenant ownership from the authenticated JWT context.

## Consequences

- Routes are isolated per tenant.
- Duplicate route codes are allowed across tenants but not within the same tenant.
- Dispatch and trip modules can reference routes as operational definitions.
- Fare changes can later evolve into route pricing history.
