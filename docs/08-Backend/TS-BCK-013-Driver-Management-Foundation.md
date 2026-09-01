---
Document ID: TS-BCK-013
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-013 — Driver Management Foundation

## Purpose

This document defines the first tenant-scoped driver management capability for TaxiSphere.

## Business Capability

Driver management allows association administrators and operations managers to register drivers, track licence and PDP information, and prepare driver records for future vehicle assignment and trip dispatch.

## Endpoints

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/drivers` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER` | List current tenant drivers |
| GET | `/api/v1/drivers/{driverId}` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER` | View one tenant driver |
| POST | `/api/v1/drivers` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER` | Create a tenant driver |

## Business Rules

- Driver operations require tenant context from a valid JWT.
- A tenant association profile must exist before drivers can be created.
- Licence numbers must be unique within a tenant.
- PDP numbers must be unique within a tenant.
- Licence and PDP expiry dates must be future dates.
- New drivers start in `PENDING_VERIFICATION` status.
- Driver lookups are always scoped by `tenant_id`.

## Data Captured

- First name and last name
- Phone number and optional email
- Licence number and expiry date
- PDP number and expiry date
- Driver status
