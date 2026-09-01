# ADR-027 — Create Tenant-Scoped Dashboard Overview

## Status

Accepted

## Context

TaxiSphere has enough operational modules to expose high-value summary metrics for association and operations users.

## Decision

Add a tenant-scoped dashboard overview endpoint that aggregates trips, revenue, drivers, vehicles, ranks, and routes.

## Consequences

- The frontend can display meaningful operational cards.
- Metrics remain isolated per tenant.
- Future dashboards can expand into charts, trends, and platform-level analytics.
