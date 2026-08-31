---
document_id: TS-DDS-001
title: TaxiSphere Database Design Specification
version: 0.1.0
status: Draft
classification: Internal Technical Design
owner: Database Architecture Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere Database Design Specification

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-DDS-001 |
| Version | 0.1.0 |
| Status | Draft |
| Owner | Database Architecture Office |
| Source Documents | TS-SRS-001, TS-SAD-001, ADR-005 |

## Executive Summary

This document defines the initial database design direction for TaxiSphere. The design uses a relational database with a shared schema and tenant-aware records. It supports the modular monolith architecture while preserving clear domain ownership and future migration options.

## 1. Database Goals

| ID | Goal |
| --- | --- |
| DG-001 | Enforce tenant ownership for tenant-scoped operational data. |
| DG-002 | Support core Release 1 entities: tenant, users, association, ranks, drivers, vehicles, routes, trips, reports, and audit logs. |
| DG-003 | Maintain referential integrity and predictable query patterns. |
| DG-004 | Support reporting without weakening tenant isolation. |
| DG-005 | Keep the schema understandable for future contributors. |

## 2. Database Strategy

TaxiSphere Version 1 uses:

- Shared relational database.
- Shared schema.
- Tenant-scoped records using `tenant_id`.
- UUID primary keys preferred for public identifiers.
- Audit fields on business-critical tables.
- Database migrations for repeatable schema changes.

## 3. Core Entity Catalogue

| Entity | Purpose | Tenant Scoped |
| --- | --- | --- |
| `tenant` | Represents an association or transport organization using TaxiSphere. | No |
| `user_account` | Stores platform and tenant user accounts. | Conditional |
| `role` | Defines application roles. | No |
| `user_role` | Maps users to roles. | Conditional |
| `association` | Stores tenant association profile information. | Yes |
| `taxi_rank` | Stores rank location, capacity, hours, and status. | Yes |
| `driver` | Stores driver identity, licence, PDP, and availability. | Yes |
| `vehicle` | Stores registration, model, capacity, status, and compliance data. | Yes |
| `route` | Stores origin, destination, fare, stops, and operating route data. | Yes |
| `trip` | Stores dispatch, lifecycle, passenger count, timing, and revenue data. | Yes |
| `audit_log` | Records security-sensitive and business-critical actions. | Conditional |

## 4. Tenant Isolation Rules

- Tenant-owned tables must include `tenant_id` unless ownership is inherited through a required parent relationship.
- Queries for tenant-owned records must filter by tenant context.
- Unique constraints for tenant-owned business values should include `tenant_id`.
- Reports must aggregate only records belonging to the resolved tenant.
- Platform-level tables must be explicitly identified as non-tenant-scoped.

## 5. Baseline Table Sketch

| Table | Key Columns | Notes |
| --- | --- | --- |
| `tenant` | `id`, `name`, `status`, `created_at`, `updated_at` | SaaS organization boundary. |
| `user_account` | `id`, `tenant_id`, `email`, `password_hash`, `status` | `tenant_id` nullable only for platform administrators. |
| `association` | `id`, `tenant_id`, `name`, `registration_number`, `status` | One main association profile per tenant initially. |
| `taxi_rank` | `id`, `tenant_id`, `name`, `address`, `capacity`, `status` | Supports rank operations and dispatch. |
| `driver` | `id`, `tenant_id`, `first_name`, `last_name`, `licence_number`, `pdp_expiry_date`, `status` | Supports compliance checks. |
| `vehicle` | `id`, `tenant_id`, `registration_number`, `model`, `capacity`, `status`, `roadworthy_expiry_date` | Supports dispatch eligibility. |
| `route` | `id`, `tenant_id`, `origin`, `destination`, `fare_amount`, `status` | Active routes are dispatchable. |
| `trip` | `id`, `tenant_id`, `route_id`, `driver_id`, `vehicle_id`, `status`, `passenger_count`, `revenue_amount` | Core operational event. |
| `audit_log` | `id`, `tenant_id`, `actor_user_id`, `action`, `resource_type`, `resource_id`, `created_at` | Tenant nullable only for platform-wide events. |

## 6. Common Columns

Tenant-owned business tables should include:

- `id`
- `tenant_id`
- `created_at`
- `updated_at`
- `created_by`
- `updated_by`
- `version`
- `deleted_at`, where soft delete is required

## 7. Indexing Principles

- Index `tenant_id` on tenant-owned tables.
- Use composite indexes for common tenant-scoped lookups.
- Include status/date indexes for dispatch, trip history, and reporting queries.
- Avoid premature indexing until query patterns are confirmed.

## 8. Data Integrity Rules

- A trip must reference a valid route, driver, vehicle, and tenant.
- Driver, vehicle, route, and trip references must not cross tenant boundaries.
- Driver licence and vehicle registration uniqueness should be evaluated per tenant.
- Completed trips should not be mutated casually; corrections require auditability.

## 9. Migration Strategy

Database schema changes should be managed through repeatable migration tooling when implementation begins. The exact migration tool will be confirmed during backend setup, with Flyway as the likely default for Spring Boot.

## 10. Open Design Items

| Item | Decision Needed |
| --- | --- |
| Primary key strategy | Confirm UUID storage format and database type. |
| Soft delete policy | Decide which tables require soft delete. |
| Reporting model | Decide whether reports query live tables or reporting views initially. |
| Audit storage | Decide audit payload format and retention policy. |
