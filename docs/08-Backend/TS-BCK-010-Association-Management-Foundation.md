---
Document ID: TS-BCK-010
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-010 — Association Management Foundation

## Purpose

This document defines the first tenant-scoped association management capability.

## Domain Decision

In TaxiSphere, a tenant is the SaaS boundary and a taxi association is the business organization operating inside that boundary.

## Endpoints

| Method | Path | Role | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/associations/me` | `ASSOCIATION_ADMIN` | Read the current tenant association profile |
| POST | `/api/v1/associations` | `ASSOCIATION_ADMIN` | Create the current tenant association profile |

## Business Rules

- Association operations require tenant context from a valid JWT.
- Each tenant can have one association profile in this release.
- Association registration numbers must be unique when provided.
- New association profiles start in `SETUP` status.

## Tenant Isolation

Association lookup is always scoped by `tenant_id`. A tenant user cannot read or create data for another tenant.
