---
document_id: ADR-005
title: Adopt the Database Strategy & Tenant Data Isolation Model
version: 1.0.0
status: Accepted
date: 2026-08-01
decision_makers:
  - Solution Architecture Office
  - Product Owner
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - TS-STD-001
  - TS-ENG-001
  - ADR-001
  - ADR-002
  - ADR-003
  - ADR-004
---

# ADR-005 – Database Strategy & Tenant Data Isolation

> **Status:** Accepted

---

# Document Control

| Property | Value |
|----------|-------|
| ADR ID | ADR-005 |
| Title | Database Strategy & Tenant Data Isolation |
| Status | Accepted |
| Version | 1.0.0 |
| Product | TaxiSphere Enterprise Mobility Platform |
| Owner | Solution Architecture Office |

---

# 1. Context

TaxiSphere is designed as a cloud-native, multi-tenant SaaS platform serving multiple transport organizations.

The database architecture must ensure:

- Strong tenant isolation
- High performance
- Data integrity
- Scalability
- Simplicity of operations
- Future evolution

---

# 2. Problem Statement

Without a well-defined database strategy:

- Tenant data may leak.
- Performance may degrade.
- Data integrity becomes difficult to maintain.
- Future scaling becomes expensive.
- Database design becomes inconsistent.

TaxiSphere requires a standardized enterprise database strategy.

---

# 3. Decision

TaxiSphere Version 1 shall adopt:

- MySQL
- Shared Database
- Shared Schema
- Tenant-aware data model
- Domain-driven table ownership
- Soft Deletes
- Audit Fields
- Foreign Key Constraints
- Optimized Indexing

---

# 4. Decision Drivers

The selected strategy must support:

- Multi-Tenant SaaS
- Modular Monolith
- High performance
- Strong security
- Operational simplicity
- Future migration options

---

# 5. Database Architecture

```
TaxiSphere

Application

↓

MySQL Database

↓

Shared Schema

↓

Tenant A

Tenant B

Tenant C

Tenant D
```

Each tenant shares the same schema while remaining logically isolated.

---

# 6. Tenant Isolation

Every business table shall contain:

```
tenant_id
```

Example:

```
driver

driver_id
tenant_id
driver_number
first_name
last_name
license_number
status
```

No business entity may exist without a tenant association.

---

# 7. Query Standard

Every business query shall include tenant filtering.

Example:

```sql
SELECT *
FROM driver
WHERE tenant_id = :tenantId;
```

Tenant filtering must never depend on client-supplied values.

The application shall derive the tenant from the authenticated user context.

---

# 8. Primary Keys

Every entity shall use a surrogate primary key.

Example:

```
driver_id
vehicle_id
trip_id
payment_id
user_id
```

Primary keys shall remain immutable.

---

# 9. Foreign Keys

Relationships shall enforce referential integrity.

Example:

```
Vehicle

↓

Driver Assignment

↓

Driver
```

All foreign key relationships shall be explicitly defined.

---

# 10. Audit Fields

Every business table shall include:

```
created_at

created_by

updated_at

updated_by
```

These fields provide complete change tracking.

---

# 11. Soft Deletes

Business records shall not normally be physically deleted.

Instead:

```
deleted

deleted_at

deleted_by
```

Soft deletes preserve history and improve auditability.

---

# 12. Status Management

Business entities shall use status values instead of deletion where appropriate.

Example:

```
ACTIVE

INACTIVE

SUSPENDED

ARCHIVED
```

---

# 13. Indexing Strategy

Indexes shall be created for:

- Primary Keys
- Foreign Keys
- tenant_id
- Frequently searched columns
- Frequently joined columns

Composite indexes may be introduced after performance analysis.

---

# 14. Transactions

Database transactions shall:

- Be short-lived
- Maintain consistency
- Roll back on failure
- Preserve ACID properties

Business operations spanning multiple tables shall execute within transactions where appropriate.

---

# 15. Data Integrity Rules

The platform shall enforce:

- NOT NULL constraints
- UNIQUE constraints
- Foreign Keys
- CHECK constraints (where supported)
- Application-level validation

---

# 16. Module Ownership

Each module owns its database objects.

Example:

```
Driver Module

driver

driver_license

driver_documents
```

Another module may access this data only through defined interfaces or services.

---

# 17. Performance Strategy

Performance shall be maintained through:

- Proper indexing
- Query optimization
- Pagination
- Avoiding unnecessary joins
- Batch processing where appropriate
- Connection pooling

---

# 18. Backup & Recovery

Operational requirements include:

- Automated backups
- Point-in-time recovery (where supported)
- Disaster recovery procedures
- Regular restore testing

---

# 19. Future Evolution

The database architecture may evolve to support:

- Database-per-Tenant
- Read Replicas
- Partitioning
- Redis Caching
- Data Warehousing
- Analytics Platform
- Event Streaming

Such changes require a dedicated ADR.

---

# 20. Risks

| Risk | Mitigation |
|------|------------|
| Cross-tenant data access | Mandatory tenant filtering and automated testing |
| Poor query performance | Indexing, query reviews, performance monitoring |
| Large database growth | Archiving strategy and capacity planning |
| Data corruption | Transactions, constraints, backups, restore testing |

---

# 21. Consequences

## Positive

- Strong tenant isolation
- Lower operational costs
- Consistent data model
- Easier maintenance
- Better scalability
- Enterprise-grade governance

## Negative

- Tenant filtering required for every query
- Shared database performance must be monitored
- Future migration to database-per-tenant requires planning

---

# 22. Compliance

This decision complies with:

- TS-STD-001 – Documentation & Engineering Standards
- TS-ENG-001 – Engineering Handbook
- ADR-001 – Multi-Tenant SaaS Architecture
- ADR-002 – Modular Monolith Architecture
- ADR-003 – Technology Stack Baseline
- ADR-004 – Authentication & Authorization Strategy

---

# Decision Summary

**Decision:** Adopt a shared database, shared schema strategy using MySQL with mandatory tenant isolation, audit fields, soft deletes, indexing, and domain-driven ownership.

**Status:** Accepted.

**Rationale:** This strategy provides an optimal balance between scalability, operational simplicity, cost efficiency, security, and maintainability while supporting TaxiSphere's cloud-native, multi-tenant SaaS architecture.