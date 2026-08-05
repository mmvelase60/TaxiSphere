---
document_id: TS-DOM-009
title: TaxiSphere Enterprise Responsibility Matrix
version: 1.0.0
status: Approved
date: 2026-08-05
owner: Enterprise Architecture
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - TS-DOM-001
  - TS-DOM-002
  - TS-DOM-004
  - TS-DOM-005
  - TS-DOM-006
  - TS-DOM-007
  - TS-DOM-008
  - ADR-002
  - ADR-011
---

# TS-DOM-009 – Enterprise Responsibility Matrix

> **Status:** Approved

---

# Document Control

| Property | Value |
|----------|-------|
| Document ID | TS-DOM-009 |
| Title | Enterprise Responsibility Matrix |
| Version | 1.0.0 |
| Status | Approved |
| Owner | Enterprise Architecture |

---

# 1. Purpose

This document defines ownership across the TaxiSphere platform.

Every business capability, aggregate, API, database schema, event, and service shall have a clearly defined owner.

Ownership ensures:

- Clear responsibilities
- Low coupling
- Independent development
- Consistent governance
- Easier scalability
- Future microservice readiness

---

# 2. Ownership Principles

Ownership rules:

- Every domain has one primary owner.
- Every Aggregate Root owns its business rules.
- Every bounded context owns its own database schema.
- Cross-domain communication occurs only through published interfaces or domain events.
- Direct database access between bounded contexts is prohibited.

---

# 3. Domain Ownership Matrix

| Domain | Primary Owner | Responsibilities |
|---------|---------------|------------------|
| Platform | Platform Management | Global configuration, feature flags, platform health |
| Tenant | Tenant Management | Tenant lifecycle, branding, subscriptions |
| Identity | Identity & Security | Users, roles, permissions, authentication |
| Fleet | Fleet Management | Fleet operations |
| Driver | Driver Management | Driver lifecycle |
| Vehicle | Vehicle Management | Vehicle lifecycle |
| Route | Route Management | Routes and stops |
| Scheduling | Scheduling & Dispatch | Shift planning and dispatch |
| Trip | Trip Management | Trip execution |
| Passenger | Passenger Services | Passenger information |
| Finance | Financial Management | Payments, invoices, refunds |
| Compliance | Compliance Management | Regulatory compliance |
| Document | Document Management | File storage and metadata |
| Notification | Notification Service | Communication |
| Reporting | Reporting & Analytics | KPIs and reports |

---

# 4. Aggregate Ownership

| Aggregate Root | Owning Context |
|----------------|----------------|
| Tenant | Tenant Management |
| User | Identity & Security |
| Driver | Driver Management |
| Vehicle | Vehicle Management |
| Route | Route Management |
| Schedule | Scheduling & Dispatch |
| Trip | Trip Management |
| Passenger | Passenger Services |
| Payment | Financial Management |
| ComplianceItem | Compliance Management |
| Document | Document Management |
| Notification | Notification Service |
| Report | Reporting & Analytics |

Only the owning context may modify its aggregate directly.

---

# 5. Database Ownership

Each bounded context owns its own schema.

| Schema | Owner |
|---------|-------|
| platform | Platform Management |
| tenant | Tenant Management |
| identity | Identity & Security |
| fleet | Fleet Management |
| driver | Driver Management |
| vehicle | Vehicle Management |
| route | Route Management |
| scheduling | Scheduling & Dispatch |
| trip | Trip Management |
| passenger | Passenger Services |
| finance | Financial Management |
| compliance | Compliance Management |
| documents | Document Management |
| notification | Notification Service |
| reporting | Reporting & Analytics |

No schema may update another schema directly.

---

# 6. API Ownership

Every REST endpoint belongs to one bounded context.

Examples:

| API | Owner |
|-----|-------|
| /api/v1/tenants | Tenant Management |
| /api/v1/users | Identity & Security |
| /api/v1/drivers | Driver Management |
| /api/v1/vehicles | Vehicle Management |
| /api/v1/routes | Route Management |
| /api/v1/schedules | Scheduling & Dispatch |
| /api/v1/trips | Trip Management |
| /api/v1/payments | Financial Management |
| /api/v1/documents | Document Management |

---

# 7. Event Ownership

Each event has one publisher.

| Event | Publisher |
|--------|-----------|
| TenantRegistered | Tenant |
| UserRegistered | Identity |
| DriverRegistered | Driver |
| DriverAssigned | Driver |
| VehicleRegistered | Vehicle |
| RouteCreated | Route |
| ScheduleCreated | Scheduling |
| TripStarted | Trip |
| TripCompleted | Trip |
| PaymentProcessed | Finance |
| DocumentUploaded | Document |
| NotificationSent | Notification |

---

# 8. Event Consumer Matrix

| Event | Consumers |
|--------|-----------|
| TenantRegistered | Identity, Billing, Notifications |
| DriverRegistered | Compliance, Reporting |
| VehicleRegistered | Fleet, Reporting |
| TripStarted | AI, Reporting, Notifications |
| TripCompleted | Finance, AI, Reporting |
| PaymentProcessed | Reporting, Notifications |
| DocumentUploaded | Compliance, Audit |

Consumers may react to events but never modify the publisher's aggregate directly.

---

# 9. Module Ownership

| Module | Owner |
|---------|-------|
| platform-module | Platform Team |
| tenant-module | Tenant Team |
| identity-module | Security Team |
| driver-module | Operations Team |
| vehicle-module | Fleet Team |
| route-module | Operations Team |
| scheduling-module | Dispatch Team |
| trip-module | Operations Team |
| finance-module | Finance Team |
| reporting-module | Analytics Team |

---

# 10. Security Ownership

Security responsibilities are centralized.

Identity & Security owns:

- Authentication
- Authorization
- JWT Tokens
- Password Policies
- MFA (Future)
- Session Management
- Security Audit

Other domains consume security services but do not implement authentication independently.

---

# 11. AI Ownership

TaxiSphere AI operates as a supporting platform capability.

AI services may consume events from:

- Trip
- Driver
- Vehicle
- Finance
- Reporting
- Passenger Feedback

AI services shall not directly modify business aggregates.

All recommendations must be reviewed by business rules before execution.

---

# 12. Integration Ownership

External integrations shall be managed through dedicated integration adapters.

Examples:

- Government systems
- Payment gateways
- Mapping services
- SMS providers
- Email providers

Each adapter belongs to the owning bounded context.

---

# 13. Team Responsibility Matrix

| Team | Responsibilities |
|------|------------------|
| Platform Team | Platform governance |
| Identity Team | Security |
| Operations Team | Drivers, Vehicles, Trips |
| Finance Team | Payments |
| Analytics Team | Reporting |
| AI Team | Prediction, Translation, Optimization |

---

# 14. Governance Principles

Ownership shall:

- Be explicit.
- Avoid duplication.
- Prevent conflicting implementations.
- Support independent deployment.
- Support future microservices.
- Align with Domain-Driven Design.

---

# 15. Future Evolution

As TaxiSphere evolves:

- Teams may own one or more bounded contexts.
- Modules may become independent microservices.
- Ownership remains unchanged even if deployment changes.
- Domain events become the preferred integration mechanism.

---

# 16. Success Criteria

The Enterprise Responsibility Matrix is successful when:

- Every domain has a single owner.
- Every Aggregate Root has one owning context.
- Every API has one owner.
- Every database schema has one owner.
- Every event has one publisher.
- Cross-domain coupling remains low.
- Teams can work independently without ownership conflicts.

---

# Document Summary

The Enterprise Responsibility Matrix defines ownership boundaries across TaxiSphere. It establishes clear accountability for business domains, aggregates, APIs, database schemas, events, integrations, security, AI capabilities, and development teams. This governance model enables scalable development, maintainable architecture, and a smooth evolution from a modular monolith to microservices.