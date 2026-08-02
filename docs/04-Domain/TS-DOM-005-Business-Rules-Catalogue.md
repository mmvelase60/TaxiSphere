---
document_id: TS-DOM-005
title: TaxiSphere Business Rules Catalogue
version: 1.0.0
status: Approved
date: 2026-08-02
owner: Enterprise Architecture
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - TS-DOM-001
  - TS-DOM-002
  - TS-DOM-003
  - TS-DOM-004
  - ADR-004
  - ADR-005
  - ADR-011
---

# TS-DOM-005 – Business Rules Catalogue

> **Status:** Approved

---

# Document Control

| Property | Value |
|----------|-------|
| Document ID | TS-DOM-005 |
| Title | Business Rules Catalogue |
| Version | 1.0.0 |
| Status | Approved |
| Owner | Enterprise Architecture |

---

# 1. Purpose

This document defines the official business rules governing the TaxiSphere Enterprise Mobility Platform.

Business rules describe mandatory operational behaviour that the platform must enforce regardless of the user interface, API, or integration point.

Every implementation must comply with these rules unless formally revised through architecture governance.

---

# 2. Rule Classification

Business rules are grouped into the following domains:

- Platform
- Tenant
- Identity & Security
- Driver
- Vehicle
- Fleet
- Route
- Scheduling & Dispatch
- Trip
- Passenger
- Finance
- Compliance
- Document Management
- Notifications

---

# 3. Rule Identifier Format

Every rule shall use a unique identifier.

Example:

```
BR-DR-001
```

Format:

```
BR

↓

Domain

↓

Sequence Number
```

Example domains:

```
PL = Platform

TN = Tenant

ID = Identity

DR = Driver

VH = Vehicle

FL = Fleet

RT = Route

SD = Scheduling

TR = Trip

PS = Passenger

FN = Finance

CP = Compliance

DC = Documents

NT = Notifications
```

---

# 4. Platform Rules

### BR-PL-001

Platform Administrators may manage all tenants.

---

### BR-PL-002

Platform configuration changes shall be recorded in the audit log.

---

### BR-PL-003

Every tenant shall remain logically isolated from every other tenant.

---

# 5. Tenant Rules

### BR-TN-001

Every business record shall belong to exactly one tenant.

---

### BR-TN-002

Tenant administrators may manage only their own tenant.

---

### BR-TN-003

Tenant branding shall not affect other tenants.

---

# 6. Identity & Security Rules

### BR-ID-001

Users must authenticate before accessing protected resources.

---

### BR-ID-002

Authorization shall be based on assigned roles and permissions.

---

### BR-ID-003

Passwords shall never be stored in plain text.

---

### BR-ID-004

Disabled users shall not authenticate.

---

### BR-ID-005

Every security event shall be audited.

---

# 7. Driver Rules

### BR-DR-001

A driver must belong to one tenant.

---

### BR-DR-002

A driver must possess a valid driver's licence before becoming active.

---

### BR-DR-003

Where applicable, a driver must possess a valid Professional Driving Permit (PrDP) before being assigned to public transport duties.

---

### BR-DR-004

A driver may not be assigned to overlapping active shifts.

---

### BR-DR-005

Inactive drivers shall not be dispatched.

---

# 8. Vehicle Rules

### BR-VH-001

A vehicle must belong to one tenant.

---

### BR-VH-002

A vehicle registration number shall be unique within a tenant.

---

### BR-VH-003

Vehicles with expired roadworthy certificates shall not be dispatched.

---

### BR-VH-004

Vehicles under maintenance shall not be assigned to trips.

---

### BR-VH-005

Every vehicle shall maintain a maintenance history.

---

# 9. Fleet Rules

### BR-FL-001

Fleet assignments shall reference valid drivers and vehicles.

---

### BR-FL-002

Fleet availability shall be recalculated after assignment changes.

---

# 10. Route Rules

### BR-RT-001

Routes shall contain at least one stop.

---

### BR-RT-002

Inactive routes shall not be scheduled.

---

### BR-RT-003

Route identifiers shall be unique within a tenant.

---

# 11. Scheduling & Dispatch Rules

### BR-SD-001

Every scheduled trip shall reference one driver.

---

### BR-SD-002

Every scheduled trip shall reference one vehicle.

---

### BR-SD-003

Dispatch shall verify driver and vehicle availability before assignment.

---

### BR-SD-004

A cancelled schedule shall not generate new trips.

---

# 12. Trip Rules

### BR-TR-001

A trip cannot begin without an assigned driver.

---

### BR-TR-002

A trip cannot begin without an assigned vehicle.

---

### BR-TR-003

A completed trip cannot return to an active state.

---

### BR-TR-004

Cancelled trips shall not accept payments.

---

### BR-TR-005

Every trip shall belong to one tenant.

---

# 13. Passenger Rules

### BR-PS-001

Passenger records shall remain associated with the owning tenant.

---

### BR-PS-002

Passenger feedback shall reference an existing trip where applicable.

---

# 14. Financial Rules

### BR-FN-001

Payments shall reference a valid business transaction.

---

### BR-FN-002

Refund amounts shall not exceed the original payment amount.

---

### BR-FN-003

Financial records shall be immutable after final posting, except through approved reversal or adjustment processes.

---

### BR-FN-004

Every financial transaction shall be audited.

---

# 15. Compliance Rules

### BR-CP-001

Expired compliance items shall be flagged for review.

---

### BR-CP-002

Compliance reminders shall be generated before expiry where configured.

---

### BR-CP-003

Compliance history shall be retained according to organizational policies.

---

# 16. Document Rules

### BR-DC-001

Every document shall belong to one tenant.

---

### BR-DC-002

Uploaded documents shall pass validation before storage.

---

### BR-DC-003

Only authorized users may download protected documents.

---

### BR-DC-004

Document metadata shall remain synchronized with stored files.

---

# 17. Notification Rules

### BR-NT-001

Notifications shall be generated using approved templates.

---

### BR-NT-002

Delivery attempts shall be recorded.

---

### BR-NT-003

Critical security notifications shall not be disabled by end users where required by organizational policy.

---

# 18. Cross-Domain Rules

- Tenant isolation shall always be enforced.
- Every Aggregate Root shall protect its invariants.
- Every API shall enforce authorization before business processing.
- Business rules shall execute within the domain layer.
- Validation logic shall be consistent across UI, API, and integrations.

---

# 19. Rule Lifecycle

Business rules may be:

- Proposed
- Approved
- Deprecated
- Retired

Changes require:

- Business review
- Architecture approval
- Documentation update
- Regression testing

---

# 20. Governance

Business rules shall:

- Have unique identifiers.
- Be version controlled.
- Be traceable to requirements.
- Be traceable to automated tests.
- Be reviewed before each major release.

---

# 21. Success Criteria

The Business Rules Catalogue is successful when:

- Every important business policy is documented.
- Every implementation references applicable rules.
- Automated tests validate rule compliance.
- New business capabilities include corresponding business rules.

---

# Document Summary

This Business Rules Catalogue establishes the operational policies that govern TaxiSphere. It provides a centralized, traceable collection of business rules that guide implementation, validation, testing, compliance, and future platform evolution while ensuring consistent behaviour across all modules.