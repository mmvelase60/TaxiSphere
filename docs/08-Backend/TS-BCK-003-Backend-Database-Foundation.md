---
document_id: TS-BCK-003
title: TaxiSphere Backend Database Foundation
version: 0.1.0
status: Draft
classification: Internal Engineering
owner: Backend Engineering Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere Backend Database Foundation

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-BCK-003 |
| Version | 0.1.0 |
| Status | Draft |
| Owner | Backend Engineering Office |
| Source Documents | TS-DDS-001, TS-SAD-001, ADR-005, ADR-012 |

## Executive Summary

This document records the first persistence foundation for TaxiSphere. It adds Spring Data JPA, MySQL runtime support, Flyway migrations, an initial `tenant` table, an `audit_log` table, and a small tenant management slice.

## 1. Added Backend Capabilities

| Capability | Implementation |
| --- | --- |
| JPA persistence | `spring-boot-starter-data-jpa` |
| MySQL support | `mysql-connector-j` runtime dependency |
| Database migrations | Flyway core and MySQL support |
| Local/test fallback | H2 in MySQL compatibility mode |
| Tenant model | `Tenant` JPA entity and `TenantStatus` enum |
| Tenant API | `GET` and `POST` endpoints under `/api/v1/platform/tenants` |
| Tenant context foundation | `TenantContext` and `TenantContextHolder` |

## 2. Migration Baseline

Initial migration:

```text
V1__create_platform_foundation.sql
```

Creates:

- `tenant`
- `audit_log`
- tenant audit index

## 3. Tenant Isolation Direction

This release establishes the tenant as the SaaS boundary. Future tenant-owned tables must include `tenant_id` or inherit tenant ownership through a required aggregate relationship.

## 4. Verification

The POM and source files can be structurally verified on this machine. Full Maven tests remain pending until Maven or a complete Maven wrapper is available.

## 5. Next Steps

- Generate the Maven wrapper JAR.
- Run `mvn test`.
- Add authentication and authorization dependencies.
- Protect platform tenant endpoints by role.
- Add tenant-aware base repository/query patterns for tenant-scoped entities.
