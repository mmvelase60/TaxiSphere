---
document_id: ADR-009
title: Adopt the File Storage & Document Management Strategy
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
  - ADR-008
---

# ADR-009 – File Storage & Document Management Strategy

> **Status:** Accepted

---

# Document Control

| Property | Value |
|----------|-------|
| ADR ID | ADR-009 |
| Title | File Storage & Document Management Strategy |
| Version | 1.0.0 |
| Status | Accepted |
| Product | TaxiSphere Enterprise Mobility Platform |
| Owner | Solution Architecture Office |

---

# 1. Context

TaxiSphere manages operational and regulatory information for transport organizations.

Many business processes require secure storage and retrieval of supporting documents, images, certificates, and generated reports.

The platform requires a scalable and secure document management strategy that supports tenant isolation and future cloud-native growth.

---

# 2. Problem Statement

Without a governed file storage strategy:

- Files may be stored inconsistently.
- Sensitive documents may be exposed.
- Database size may grow unnecessarily.
- Backup complexity increases.
- Compliance becomes difficult.
- Tenant isolation may be compromised.

---

# 3. Decision

TaxiSphere shall separate business data from binary file storage.

Business metadata shall remain in MySQL.

Files shall be stored externally.

Version 1:

- Local configurable storage (development)
- Container-mounted persistent storage (self-hosted deployments)

Production Target:

- Azure Blob Storage

---

# 4. Decision Drivers

The selected strategy shall provide:

- Tenant isolation
- Scalability
- Security
- Cost efficiency
- Backup simplicity
- Cloud readiness
- High availability

---

# 5. Storage Architecture

```
Angular Client

↓

Spring Boot API

↓

Document Service
