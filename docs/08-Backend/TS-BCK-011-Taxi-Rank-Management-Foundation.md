---
Document ID: TS-BCK-011
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-011 — Taxi Rank Management Foundation

## Purpose

This document defines the first taxi rank management capability for tenant-scoped operations.

## Business Capability

Taxi rank management allows association administrators and rank managers to register and view the physical taxi ranks operated by a taxi association.

## Endpoints

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/ranks` | `ASSOCIATION_ADMIN`, `RANK_MANAGER`, `DISPATCHER`, `OPERATIONS_MANAGER` | List current tenant ranks |
| GET | `/api/v1/ranks/{rankId}` | `ASSOCIATION_ADMIN`, `RANK_MANAGER`, `DISPATCHER`, `OPERATIONS_MANAGER` | View one tenant rank |
| POST | `/api/v1/ranks` | `ASSOCIATION_ADMIN`, `RANK_MANAGER` | Create a tenant rank |

## Business Rules

- Rank operations require tenant context from a valid JWT.
- A tenant association profile must exist before ranks can be created.
- Rank codes must be unique within a tenant.
- New ranks start in `SETUP` status.
- Rank lookups are always scoped by `tenant_id`.

## Data Captured

- Rank name
- Rank code
- Address
- City
- Province
- Capacity
- GPS latitude and longitude
- Operating hours
