---
document_id: ADR-008
title: Adopt the Caching Strategy
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
  - ADR-005
  - ADR-006
  - ADR-007
---

# ADR-008 – Caching Strategy

> **Status:** Accepted

---

# Document Control

| Property | Value |
|----------|-------|
| ADR ID | ADR-008 |
| Title | Caching Strategy |
| Version | 1.0.0 |
| Status | Accepted |
| Product | TaxiSphere Enterprise Mobility Platform |
| Owner | Solution Architecture Office |

---

# 1. Context

TaxiSphere is a cloud-native, multi-tenant SaaS platform that will manage drivers, vehicles, routes, schedules, trips, payments, reporting, and administration.

As the number of tenants and users grows, repeatedly querying the database for frequently requested information can reduce application performance and increase infrastructure costs.

Caching improves response times while reducing unnecessary database load.

---

# 2. Problem Statement

Without a caching strategy:

- Database load increases unnecessarily.
- API response times become slower.
- Infrastructure costs increase.
- User experience degrades.
- Scalability becomes more difficult.

TaxiSphere requires a governed caching strategy that improves performance while maintaining data consistency and tenant isolation.

---

# 3. Decision

TaxiSphere Version 1 shall adopt a layered caching strategy.

Version 1:

- Spring Cache
- In-memory caching
- Method-level caching

Future Versions:

- Redis
- Distributed caching
- Cache clustering

---

# 4. Decision Drivers

The caching strategy shall provide:

- Faster API responses
- Lower database load
- Improved scalability
- Tenant-aware isolation
- Operational simplicity
- Predictable cache behaviour

---

# 5. Caching Principles

TaxiSphere shall cache:

- Frequently read
- Rarely modified
- Expensive to compute
- Safe to reuse

The platform shall not cache highly volatile or security-sensitive information unless specifically approved.

---

# 6. Cache Architecture

```
Client

↓

REST API

↓

Spring Service

↓

Cache

↓
