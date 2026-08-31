---
document_id: TS-SRS-001
title: TaxiSphere Software Requirements Specification
version: 0.1.0
status: Draft
classification: Internal Requirements
owner: Solution Architecture Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere Software Requirements Specification

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-SRS-001 |
| Version | 0.1.0 |
| Status | Draft |
| Owner | Solution Architecture Office |
| Source Document | TS-BRD-001 |
| Related Documents | TS-VSN-001, TS-CHR-001, TS-USR-001, TS-USE-001, TS-RTM-001 |

## Revision History

| Version | Date | Author | Description |
| --- | --- | --- | --- |
| 0.1.0 | 2026-08-31 | TaxiSphere Engineering | Initial SRS draft derived from TS-BRD-001. |

## Executive Summary

This Software Requirements Specification defines the initial system requirements for TaxiSphere, a multi-tenant SaaS platform for taxi association operations. It translates the business requirements from TS-BRD-001 into functional, non-functional, security, data, integration, reporting, and acceptance requirements that will guide architecture, design, implementation, and testing.

## 1. Purpose

The purpose of this SRS is to define what the TaxiSphere platform must provide in Release 1.0 and what quality attributes the implementation must satisfy.

## 2. Scope

TaxiSphere Release 1.0 includes tenant onboarding, identity and access management, association management, rank operations, driver management, vehicle management, route management, dispatch, trip tracking, audit logging, dashboards, and operational reporting.

Release 1.0 excludes native mobile apps, payment gateway integration, digital wallet, live GPS tracking, AI demand prediction, and government integrations.

## 3. Product Overview

TaxiSphere is a shared SaaS application where each taxi association operates as an isolated tenant. Authorized users manage their tenant's operational data through a web application backed by a Spring Boot API and a relational database.

The platform must enforce tenant isolation on every tenant-owned operation.

## 4. User Classes

| User Class | Description |
| --- | --- |
| Platform Administrator | Manages platform-level tenant onboarding, activation, suspension, and governance. |
| Association Administrator | Manages tenant users, association settings, and operational configuration. |
| Rank Manager | Oversees rank activity, queues, dispatch status, and operational performance. |
| Dispatcher | Assigns drivers and vehicles to routes and manages trip dispatch workflows. |
| Taxi Owner | Views and manages vehicle-related operational information for owned vehicles. |
| Driver | Views assignments and participates in trip workflows. |
| Finance Officer | Reviews revenue, trip history, and operational reports. |
| Operations Manager | Reviews dashboards, KPIs, and performance trends. |

## 5. Functional Requirements

### 5.1 Tenant Management

| ID | Requirement | Priority | Source |
| --- | --- | --- | --- |
| FR-TEN-001 | The system shall allow a Platform Administrator to create a tenant for a taxi association. | Must | BR-001 |
| FR-TEN-002 | The system shall store tenant identity, status, contact details, and onboarding metadata. | Must | BR-001 |
| FR-TEN-003 | The system shall allow a Platform Administrator to activate, suspend, and deactivate tenants. | Must | BR-001 |
| FR-TEN-004 | The system shall prevent suspended tenants from accessing operational capabilities. | Must | BR-002 |

### 5.2 Identity and Access Management

| ID | Requirement | Priority | Source |
| --- | --- | --- | --- |
| FR-IAM-001 | The system shall authenticate users before allowing access to protected functionality. | Must | BR-002 |
| FR-IAM-002 | The system shall assign each user to a tenant, except platform-level administrative users. | Must | BR-002 |
| FR-IAM-003 | The system shall authorize actions using role-based access control. | Must | BR-002 |
| FR-IAM-004 | The system shall prevent users from accessing data outside their authorized tenant. | Must | BR-002 |
| FR-IAM-005 | The system shall support user activation, deactivation, and role assignment workflows. | Must | BR-003 |

### 5.3 Association Management

| ID | Requirement | Priority | Source |
| --- | --- | --- | --- |
| FR-ASC-001 | The system shall allow authorized users to maintain association profile information. | Must | BR-003 |
| FR-ASC-002 | The system shall allow authorized users to configure association operating details. | Should | BR-003 |
| FR-ASC-003 | The system shall associate ranks, users, vehicles, drivers, routes, and reports with a tenant association. | Must | BR-001 |

### 5.4 Rank Operations

| ID | Requirement | Priority | Source |
| --- | --- | --- | --- |
| FR-RNK-001 | The system shall allow authorized users to create and maintain taxi rank records. | Must | BR-004 |
| FR-RNK-002 | The system shall store rank location, capacity, operating hours, and status. | Should | BR-004 |
| FR-RNK-003 | The system shall support visibility into rank queue and dispatch activity. | Should | BR-007 |

### 5.5 Driver Management

| ID | Requirement | Priority | Source |
| --- | --- | --- | --- |
| FR-DRV-001 | The system shall allow authorized users to create and update driver profiles. | Must | BR-004 |
| FR-DRV-002 | The system shall store driver contact, licence, PDP, tenant, and availability information. | Must | BR-004 |
| FR-DRV-003 | The system shall identify drivers with expired or missing compliance information. | Should | BR-004 |
| FR-DRV-004 | The system shall prevent a driver from being assigned to more than one active trip. | Must | RULE-004 |

### 5.6 Vehicle Management

| ID | Requirement | Priority | Source |
| --- | --- | --- | --- |
| FR-VEH-001 | The system shall allow authorized users to create and update vehicle profiles. | Must | BR-004 |
| FR-VEH-002 | The system shall store registration, model, capacity, owner, tenant, status, and compliance information. | Must | BR-004 |
| FR-VEH-003 | The system shall prevent unavailable or non-compliant vehicles from being dispatched. | Must | RULE-005 |
| FR-VEH-004 | The system shall support visibility into vehicle utilization and trip history. | Should | BR-007 |

### 5.7 Route Management

| ID | Requirement | Priority | Source |
| --- | --- | --- | --- |
| FR-RTE-001 | The system shall allow authorized users to create and update route records. | Must | BR-004 |
| FR-RTE-002 | The system shall store route origin, destination, fare, stops, distance, estimated duration, and tenant. | Must | BR-004 |
| FR-RTE-003 | The system shall make active routes available to dispatch workflows. | Must | BR-005 |

### 5.8 Dispatch and Trip Management

| ID | Requirement | Priority | Source |
| --- | --- | --- | --- |
| FR-TRP-001 | The system shall allow a Dispatcher to create a trip for a valid tenant route. | Must | BR-005 |
| FR-TRP-002 | The system shall require a trip to have a valid tenant, route, driver, and vehicle. | Must | RULE-006 |
| FR-TRP-003 | The system shall capture passenger count, departure time, arrival time, trip status, and revenue. | Must | BR-006 |
| FR-TRP-004 | The system shall support trip status changes such as planned, dispatched, in progress, completed, and cancelled. | Must | BR-006 |
| FR-TRP-005 | The system shall prevent completion of trips with missing required operational data. | Should | BR-006 |

### 5.9 Reporting and Dashboards

| ID | Requirement | Priority | Source |
| --- | --- | --- | --- |
| FR-RPT-001 | The system shall provide dashboard views for operational KPIs. | Should | BR-007 |
| FR-RPT-002 | The system shall support daily, weekly, and monthly operational reports. | Should | BR-008 |
| FR-RPT-003 | The system shall provide reports filtered by tenant, route, driver, vehicle, rank, and date range where applicable. | Should | BR-008 |
| FR-RPT-004 | The system shall provide revenue and trip history summaries for authorized finance users. | Should | BR-008 |

### 5.10 Audit Logging

| ID | Requirement | Priority | Source |
| --- | --- | --- | --- |
| FR-AUD-001 | The system shall record security-sensitive and business-critical actions in an audit log. | Must | BR-009 |
| FR-AUD-002 | Audit records shall include actor, action, timestamp, tenant context, and affected resource where applicable. | Must | BR-009 |
| FR-AUD-003 | The system shall audit tenant suspension, role changes, driver creation, vehicle updates, and trip completion. | Must | RULE-007 |

## 6. Non-Functional Requirements

| ID | Category | Requirement | Priority |
| --- | --- | --- | --- |
| NFR-SEC-001 | Security | The system shall enforce authentication and authorization for protected resources. | Must |
| NFR-SEC-002 | Security | Tenant isolation shall be validated through automated tests for tenant-owned data. | Must |
| NFR-PER-001 | Performance | Common API requests should respond within 500 ms under expected early-stage load. | Should |
| NFR-PER-002 | Performance | Common dashboard views should load within 2 seconds under expected early-stage load. | Should |
| NFR-SCL-001 | Scalability | The architecture shall support multiple tenants from a single deployment. | Must |
| NFR-MNT-001 | Maintainability | Backend modules shall be organized by business capability. | Must |
| NFR-OBS-001 | Observability | The system shall provide structured logs for important application and security events. | Should |
| NFR-REL-001 | Reliability | The system shall protect critical operational data from accidental loss through persistence and validation controls. | Must |
| NFR-DEP-001 | Deployment | The system shall support local Docker-based deployment during early releases. | Should |

## 7. Data Requirements

The initial domain data model must support:

- Tenant.
- User.
- Role.
- Association.
- Rank.
- Driver.
- Vehicle.
- Route.
- Trip.
- Report.
- Audit Log.

Every tenant-owned entity must include tenant ownership or be reachable only through a tenant-owned aggregate.

## 8. Interface Requirements

### 8.1 User Interface

The web application shall provide role-appropriate views for platform administration, association administration, operations, dispatch, finance, and reporting.

### 8.2 API

The backend shall expose REST APIs documented with OpenAPI. API design must follow the standards defined by ADR-006.

### 8.3 External Integrations

Release 1.0 does not require payment gateway, SMS, live GPS, government, or accounting integrations. Integration points should be designed so these can be added in future releases without redesigning the core domain.

## 9. Security Requirements

- All protected requests must include authenticated user context.
- Tenant-scoped requests must resolve tenant context before accessing tenant-owned resources.
- Role checks must be applied to privileged actions.
- Passwords must be stored using a secure one-way hashing algorithm.
- Secrets must not be committed to source control.
- Audit logs must record security-sensitive actions.

## 10. Acceptance Criteria

- Functional requirements are numbered, prioritized, and traceable to BRD requirements or business rules.
- Non-functional requirements define measurable quality expectations where possible.
- Tenant isolation is treated as a core requirement, not an optional feature.
- Release 1.0 exclusions are explicit.
- This SRS can drive user stories, use cases, API design, database design, and test planning.

## 11. Related Documents

- TS-BRD-001: Business Requirements Document.
- TS-USR-001: User Stories.
- TS-USE-001: Use Cases.
- TS-RTM-001: Requirements Traceability Matrix.
- ADR-001: Multi-Tenant SaaS.
- ADR-002: Modular Monolith Architecture.
- ADR-006: API Design Standards.
