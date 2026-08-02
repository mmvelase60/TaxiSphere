---
document_id: TS-DOM-002
title: TaxiSphere Bounded Context Map
version: 1.0.0
status: Approved
date: 2026-08-02
owner: Enterprise Architecture
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - TS-DOM-001
  - ADR-001
  - ADR-002
  - ADR-011
---

# TS-DOM-002 – Bounded Context Map

> **Status:** Approved

---

# Document Control

| Property | Value |
|----------|-------|
| Document ID | TS-DOM-002 |
| Title | Bounded Context Map |
| Version | 1.0.0 |
| Status | Approved |
| Owner | Enterprise Architecture |

---

# 1. Purpose

This document defines the bounded contexts that make up the TaxiSphere Enterprise Mobility Platform.

Each bounded context represents an independent business capability with its own:

- Business rules
- Data model
- REST APIs
- Services
- User Interface
- Documentation
- Ownership

The purpose is to ensure strong separation of concerns while enabling controlled collaboration between domains.

---

# 2. Domain-Driven Design Principles

TaxiSphere adopts the following DDD principles:

- High cohesion
- Low coupling
- Clear business ownership
- Explicit boundaries
- Ubiquitous language
- Autonomous evolution
- Technology independence

Every bounded context owns its own business logic and data.

---

# 3. Strategic Context Map

```
                    +----------------------+
                    | Platform Management  |
                    +----------+-----------+
                               |
                               ▼
                    +----------------------+
                    | Tenant Management    |
                    +----------+-----------+
                               |
                               ▼
                    +----------------------+
                    | Identity & Security  |
                    +----------+-----------+
                               |
        +----------------------+----------------------+
        |                      |                      |
        ▼                      ▼                      ▼
+----------------+     +----------------+     +----------------+
| Fleet          |     | Driver         |     | Vehicle        |
| Management     |     | Management     |     | Management     |
+-------+--------+     +-------+--------+     +-------+--------+
        \                  |                      /
         \                 |                     /
          +----------------+--------------------+
                           |
                           ▼
                 +----------------------+
                 | Route Management     |
                 +----------+-----------+
                            |
                            ▼
                 +----------------------+
                 | Scheduling & Dispatch|
                 +----------+-----------+
                            |
                            ▼
                 +----------------------+
                 | Trip Management      |
                 +----------+-----------+
                            |
              +-------------+--------------+
              |                            |
              ▼                            ▼
    +--------------------+       +--------------------+
    | Passenger Services |       | Financial Mgmt     |
    +---------+----------+       +----------+---------+
              \                            /
               \                          /
                +------------------------+
                           |
                           ▼
                 +----------------------+
                 | Reporting & Analytics|
                 +----------------------+

Supporting Contexts:

- Compliance Management
- Document Management
- Notifications
```

---

# 4. Bounded Context Catalogue

TaxiSphere Version 1 consists of the following bounded contexts.

| Context | Purpose |
|----------|---------|
| Platform Management | Platform governance and configuration |
| Tenant Management | Multi-tenant administration |
| Identity & Security | Authentication and authorization |
| Fleet Management | Fleet administration |
| Driver Management | Driver lifecycle |
| Vehicle Management | Vehicle lifecycle |
| Route Management | Transport routes |
| Scheduling & Dispatch | Operational scheduling |
| Trip Management | Transport execution |
| Passenger Services | Passenger information |
| Financial Management | Payments and billing |
| Compliance Management | Regulatory compliance |
| Document Management | File storage |
| Notifications | Platform communication |
| Reporting & Analytics | Business intelligence |

---

# 5. Core Domains

Core domains provide TaxiSphere's primary business value.

These include:

- Driver Management
- Vehicle Management
- Route Management
- Scheduling & Dispatch
- Trip Management
- Financial Management

These domains receive the highest architectural priority.

---

# 6. Supporting Domains

Supporting domains enable the core business.

These include:

- Compliance Management
- Document Management
- Notifications
- Reporting
- Fleet Management

---

# 7. Generic Domains

Generic domains provide reusable platform capabilities.

These include:

- Platform Management
- Tenant Management
- Identity & Security

---

# 8. Context Responsibilities

## Platform Management

Responsible for:

- Global configuration
- Platform health
- Feature management
- Operational governance

---

## Tenant Management

Responsible for:

- Tenant lifecycle
- Tenant branding
- Subscription information
- Regional settings

---

## Identity & Security

Responsible for:

- Users
- Roles
- Permissions
- Authentication
- Authorization
- Session management

---

## Driver Management

Responsible for:

- Driver profiles
- Licences
- PrDP
- Driver availability
- Driver assignments

---

## Vehicle Management

Responsible for:

- Vehicle registration
- Maintenance
- Roadworthy certificates
- Insurance
- Vehicle inspections

---

## Route Management

Responsible for:

- Routes
- Stops
- Distance
- Fare zones

---

## Scheduling & Dispatch

Responsible for:

- Shift scheduling
- Vehicle allocation
- Driver allocation
- Daily dispatch

---

## Trip Management

Responsible for:

- Trip planning
- Trip execution
- Trip completion
- Live trip status

---

## Financial Management

Responsible for:

- Fare calculation
- Payments
- Billing
- Invoices
- Refunds

---

## Compliance Management

Responsible for:

- Compliance monitoring
- Regulatory reporting
- Expiry management

---

## Document Management

Responsible for:

- Document storage
- Metadata
- Upload
- Download
- Retention

---

## Notifications

Responsible for:

- Email
- In-app notifications
- Templates
- Delivery tracking

---

## Reporting & Analytics

Responsible for:

- Dashboards
- KPIs
- Reports
- Trend analysis

---

# 9. Context Relationships

TaxiSphere uses controlled collaboration between bounded contexts.

Relationship principles:

- Service interfaces
- REST APIs
- Internal application services
- Future domain events

Direct database access between contexts is prohibited.

---

# 10. Shared Kernel

The following platform components are shared:

- Logging
- Security Framework
- Configuration
- Error Handling
- Validation Framework
- Common Utilities

Business logic is never part of the Shared Kernel.

---

# 11. Anti-Corruption Layer

Future integrations with external systems shall use an Anti-Corruption Layer (ACL).

Examples:

- Government systems
- Banking APIs
- Insurance providers
- Payment gateways
- Mapping services

The ACL protects TaxiSphere's domain model from external changes.

---

# 12. Context Ownership

Every bounded context owns:

- Database schema
- Business rules
- REST endpoints
- Services
- Validation
- Tests
- Documentation

Ownership is exclusive.

---

# 13. Future Evolution

The bounded context model supports:

- Modular Monolith
- Event-driven architecture
- Independent deployment
- Microservices
- Independent scaling
- Team autonomy

---

# 14. Governance Principles

Bounded contexts shall:

- Maintain clear responsibilities.
- Avoid unnecessary coupling.
- Protect internal implementation details.
- Expose stable interfaces.
- Follow enterprise architecture standards.

---

# 15. Success Criteria

The architecture is successful when:

- Every business capability belongs to one bounded context.
- No context owns another context's data.
- APIs respect context ownership.
- Teams can work independently.
- Future services can be extracted without redesign.

---

# Document Summary

This Bounded Context Map establishes the strategic Domain-Driven Design structure for TaxiSphere. It defines ownership, business boundaries, and collaboration rules that guide platform implementation, long-term maintainability, and future architectural evolution.