# ADR-020 — Model Taxi Ranks as Tenant-Owned Operational Assets

## Status

Accepted

## Context

Taxi ranks are physical operational locations owned and managed by a taxi association. In a SaaS platform, ranks must be isolated by tenant and linked to the association profile.

## Decision

Create a tenant-owned `taxi_rank` table linked to `taxi_association`. Rank management endpoints resolve tenant ownership from the authenticated JWT context.

## Consequences

- Taxi ranks are isolated per tenant.
- Duplicate rank codes are allowed across different tenants but not within the same tenant.
- Dispatch, route, trip, and queue modules can reference ranks as operational anchors.
