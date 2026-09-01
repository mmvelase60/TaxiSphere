---
Document ID: TS-BCK-009
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-009 — Tenant Onboarding Foundation

## Purpose

This document defines the first enterprise onboarding flow for TaxiSphere tenants.

## Capability

Tenant onboarding allows a platform administrator to register a taxi association as a new SaaS tenant and create the first association administrator in one transaction.

## Endpoint

| Method | Path | Authorization |
| --- | --- | --- |
| POST | `/api/v1/platform/tenants` | `PLATFORM_ADMIN` |

## Request

```json
{
  "name": "Pretoria Taxi Association",
  "contactEmail": "office@pta-taxi.example",
  "adminEmail": "admin@pta-taxi.example",
  "adminPassword": "strong-local-password"
}
```

## Response

```json
{
  "id": "<tenant-id>",
  "name": "Pretoria Taxi Association",
  "contactEmail": "office@pta-taxi.example",
  "status": "SETUP",
  "adminUserId": "<user-id>",
  "createdAt": "<timestamp>",
  "updatedAt": "<timestamp>"
}
```

## Business Rules

- Tenant names must be unique.
- Tenant contact emails must be unique.
- Association admin emails must be unique across the platform.
- New tenants start in `SETUP` status.
- The first tenant user receives `ASSOCIATION_ADMIN`.
- Tenant creation and admin creation happen in one transaction.

## Traceability

| Source | Reference |
| --- | --- |
| Business Capability | Platform Management |
| Security Requirement | Tenant isolation and RBAC |
| ADR | ADR-018 |
