---
Document ID: TS-BCK-014
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-014 — Vehicle Management Foundation

## Purpose

This document defines the first tenant-scoped vehicle management capability for TaxiSphere.

## Business Capability

Vehicle management allows association administrators and operations managers to register fleet assets, track roadworthy and insurance expiry dates, and prepare vehicles for future driver assignment and dispatch.

## Endpoints

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/vehicles` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER` | List current tenant vehicles |
| GET | `/api/v1/vehicles/{vehicleId}` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER` | View one tenant vehicle |
| POST | `/api/v1/vehicles` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER` | Create a tenant vehicle |

## Business Rules

- Vehicle operations require tenant context from a valid JWT.
- A tenant association profile must exist before vehicles can be created.
- Registration numbers must be unique within a tenant.
- VIN values must be unique within a tenant when provided.
- Roadworthy and insurance expiry dates must be future dates.
- New vehicles start in `PENDING_VERIFICATION` status.
- Vehicle lookups are always scoped by `tenant_id`.

## Data Captured

- Registration number
- Make, model, and model year
- Seating capacity
- Optional VIN
- Roadworthy expiry date
- Insurance expiry date
- Vehicle status
