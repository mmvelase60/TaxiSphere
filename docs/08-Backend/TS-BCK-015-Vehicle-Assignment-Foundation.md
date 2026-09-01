---
Document ID: TS-BCK-015
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-015 — Vehicle Assignment Foundation

## Purpose

This document defines the first driver-to-vehicle assignment capability for TaxiSphere.

## Business Capability

Vehicle assignments connect verified drivers to fleet vehicles. This relationship is required before dispatch and trip management can reliably determine which driver is operating which vehicle.

## Endpoints

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/vehicle-assignments` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER` | List current tenant assignments |
| GET | `/api/v1/vehicle-assignments/{assignmentId}` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER` | View one tenant assignment |
| POST | `/api/v1/vehicle-assignments` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER` | Create an active assignment |

## Business Rules

- Assignment operations require tenant context from a valid JWT.
- Driver and vehicle must belong to the current tenant.
- A driver may have only one active vehicle assignment.
- A vehicle may have only one active driver assignment.
- New assignments start in `ACTIVE` status.
- Assignment lookups are always scoped by `tenant_id`.
