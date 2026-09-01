# ADR-032 — Create Tenant-Scoped Vehicle Maintenance Records

## Status

Accepted

## Context

TaxiSphere has fleet, compliance, finance, and notification foundations. Vehicle maintenance is required to support reliable taxi operations, compliance readiness, and future cost analytics.

## Decision

Create a tenant-scoped `MaintenanceRecord` model linked to vehicles. Maintenance records track type, status, scheduled date, completed date, cost, service provider, and description.

## Consequences

- Associations can track vehicle service and repair history.
- Maintenance records remain isolated by tenant and linked to tenant vehicles.
- Future finance integration can convert completed maintenance into expense transactions.
- Future notification integration can send maintenance reminders.