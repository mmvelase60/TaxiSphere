---
Document ID: TS-BCK-012
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-012 — Route Management Foundation

## Purpose

This document defines the first route and fare management capability for TaxiSphere.

## Business Capability

Route management allows association administrators and rank managers to define taxi routes, fares, travel distance, and estimated travel time.

## Endpoints

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/routes` | `ASSOCIATION_ADMIN`, `RANK_MANAGER`, `DISPATCHER`, `OPERATIONS_MANAGER` | List current tenant routes |
| GET | `/api/v1/routes/{routeId}` | `ASSOCIATION_ADMIN`, `RANK_MANAGER`, `DISPATCHER`, `OPERATIONS_MANAGER` | View one tenant route |
| POST | `/api/v1/routes` | `ASSOCIATION_ADMIN`, `RANK_MANAGER` | Create a tenant route |

## Business Rules

- Route operations require tenant context from a valid JWT.
- A tenant association profile must exist before routes can be created.
- Route codes must be unique within a tenant.
- Origin rank, when supplied, must belong to the same tenant.
- New routes start in `SETUP` status.
- Route lookups are always scoped by `tenant_id`.

## Data Captured

- Route code
- Origin and destination
- Fare
- Distance in kilometers
- Estimated travel time
- Optional origin rank
