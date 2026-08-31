---
document_id: TS-BRD-001
title: TaxiSphere Business Requirements Document
version: 0.1.0
status: Draft
classification: Internal Business Analysis
owner: Business Analysis Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere Business Requirements Document

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-BRD-001 |
| Version | 0.1.0 |
| Status | Draft |
| Owner | Business Analysis Office |
| Classification | Internal Business Analysis |
| Related Documents | TS-VSN-001, TS-CHR-001, TS-SRS-001 |

## Revision History

| Version | Date | Author | Description |
| --- | --- | --- | --- |
| 0.1.0 | 2026-08-31 | TaxiSphere Engineering | Initial business requirements draft. |

## Executive Summary

TaxiSphere is being created to solve operational and reporting problems in taxi association environments. The platform will provide a shared SaaS application where many associations can manage their own users, ranks, vehicles, drivers, routes, trips, finance workflows, and reports while keeping tenant data isolated.

This BRD defines the business needs, goals, stakeholders, scope, business requirements, KPIs, assumptions, constraints, and risks that will drive the Software Requirements Specification.

## 1. Business Background

Taxi association operations often depend on manual processes such as paper registers, informal dispatch queues, verbal communication, cash reconciliation, and spreadsheet-based reporting. These methods can work at small scale, but they create growing problems when associations need better visibility, accountability, compliance, and operational planning.

TaxiSphere is intended to provide a professional digital operating platform for this domain.

## 2. Business Problem Statement

The current operating environment creates the following business problems:

- Dispatch and queue activity is difficult to monitor consistently.
- Driver, vehicle, and route records are fragmented.
- Revenue reporting depends heavily on manual capture and reconciliation.
- Association leaders have limited real-time visibility into operations.
- Compliance information such as licences, PDP dates, insurance, and roadworthy status is difficult to track.
- Passenger demand patterns are hard to analyze.
- Scaling operations across multiple ranks or associations creates duplicated administration.

## 3. Business Objectives

| ID | Objective | Description |
| --- | --- | --- |
| BO-001 | Digitize taxi association operations | Replace manual and fragmented processes with controlled digital workflows. |
| BO-002 | Improve operational visibility | Give authorized users a reliable view of ranks, drivers, vehicles, routes, trips, and performance. |
| BO-003 | Improve financial transparency | Capture trip and revenue information in a way that supports reporting and reconciliation. |
| BO-004 | Improve compliance readiness | Track driver and vehicle compliance information before operational assignment. |
| BO-005 | Reduce administrative overhead | Reduce duplicate data capture, manual registers, and spreadsheet dependency. |
| BO-006 | Enable scalable SaaS growth | Support many associations from one shared platform while keeping data isolated. |

## 4. Business Scope

### 4.1 In Scope

- Tenant onboarding and association setup.
- User and role management.
- Driver profile management.
- Vehicle profile and compliance management.
- Taxi rank and queue visibility.
- Route and fare management.
- Trip dispatch and trip lifecycle tracking.
- Revenue and operational reporting.
- Audit logging for important business events.
- Dashboard and KPI visibility.

### 4.2 Out of Scope for Version 1.0

- Passenger mobile application.
- Driver mobile application.
- Online booking.
- Card payment gateway.
- Digital wallet.
- Live GPS tracking.
- AI demand prediction.
- Government integrations.
- Multi-country regulatory configuration.

## 5. Stakeholders

| Stakeholder | Business Interest |
| --- | --- |
| Platform Owner | Wants a scalable product that can serve many associations. |
| Platform Administrator | Manages tenant onboarding, activation, and platform governance. |
| Association Administrator | Manages users, association configuration, and tenant operations. |
| Rank Manager | Needs visibility into rank activity, queues, and dispatch performance. |
| Dispatcher | Needs fast workflows for assigning vehicles and starting trips. |
| Taxi Owner | Needs visibility into assigned vehicles, drivers, trips, and compliance status. |
| Driver | Needs clear assignment and trip information. |
| Finance Officer | Needs reliable revenue, trip, and reconciliation reports. |
| Operations Manager | Needs dashboards and performance insights. |
| Passenger | Will need route, fare, ticket, and notification services in future releases. |

## 6. Business Requirements

| ID | Requirement | Priority |
| --- | --- | --- |
| BR-001 | The platform shall support multiple taxi associations as independent tenants. | Must |
| BR-002 | The platform shall ensure users can only access data authorized for their tenant and role. | Must |
| BR-003 | The platform shall allow authorized users to manage association profiles and operating information. | Must |
| BR-004 | The platform shall allow authorized users to manage ranks, routes, fares, drivers, and vehicles. | Must |
| BR-005 | The platform shall support dispatch workflows for assigning vehicles and drivers to routes. | Must |
| BR-006 | The platform shall capture trip information for operational and financial reporting. | Must |
| BR-007 | The platform shall provide dashboards for core operational KPIs. | Should |
| BR-008 | The platform shall support reports for daily, weekly, and monthly operations. | Should |
| BR-009 | The platform shall maintain audit records for security-sensitive and business-critical actions. | Must |
| BR-010 | The platform shall support future passenger, payment, mobile, and analytics capabilities without major redesign. | Should |
| BR-011 | The platform shall provide a documented architecture and requirements trail for future contributors. | Must |
| BR-012 | The platform shall support cloud-ready deployment practices. | Should |

## 7. Business Rules

| ID | Rule |
| --- | --- |
| RULE-001 | A tenant represents one taxi association or transport organization. |
| RULE-002 | Tenant-owned operational data must belong to exactly one tenant. |
| RULE-003 | A user must have an assigned role before accessing protected platform capabilities. |
| RULE-004 | A driver cannot be assigned to more than one active trip at the same time. |
| RULE-005 | A vehicle cannot be dispatched if it is inactive, unavailable, or non-compliant. |
| RULE-006 | A trip must be associated with a valid route, vehicle, driver, and tenant. |
| RULE-007 | Critical actions such as tenant suspension, user role changes, and trip completion must be audited. |

## 8. Key Performance Indicators

| KPI | Target |
| --- | --- |
| Tenant onboarding duration | Under 30 minutes for standard tenant setup. |
| Dashboard response time | Under 2 seconds for common dashboard views. |
| Common API response time | Under 500 ms under expected early-stage load. |
| Tenant isolation defects | Zero known cross-tenant data access defects. |
| Report generation | Daily operational reports available for selected date ranges. |
| Documentation traceability | Major features mapped to business requirements before implementation. |

## 9. Business Process Areas

| Process Area | Summary |
| --- | --- |
| Tenant Onboarding | Platform administrator registers and activates an association tenant. |
| User Administration | Association administrator invites, manages, and deactivates users. |
| Driver Management | Authorized users manage driver profiles, licence details, PDP dates, and availability. |
| Vehicle Management | Authorized users manage vehicle records, capacity, ownership, status, and compliance. |
| Route Management | Authorized users define routes, fares, stops, and operating information. |
| Dispatch Operations | Dispatcher assigns vehicles and drivers to routes and starts trips. |
| Trip Management | System records passenger count, departure, arrival, status, and revenue information. |
| Reporting | Finance and operations users review revenue, trip, vehicle, and driver reports. |

## 10. Assumptions

- The first product release is web-based.
- Taxi associations can be modeled as tenants.
- Associations will manage their own operational users.
- Early releases prioritize administration and operations before passenger-facing features.
- Payment and AI capabilities will be introduced after the core operational model is stable.

## 11. Constraints

- The platform must enforce tenant isolation.
- The platform must use role-based access control.
- The platform must be designed for cloud deployment.
- Documentation must remain version controlled.
- Technology choices must use stable ecosystem support at the time of implementation.
- The first implementation should stay achievable for a small engineering team.

## 12. Risks and Mitigations

| Risk | Impact | Mitigation |
| --- | --- | --- |
| Tenant isolation failure | Critical | Treat tenant isolation as a security requirement and test it directly. |
| Manual workflows are misunderstood | High | Capture use cases and validate business process flows before implementation. |
| Scope expands too quickly | High | Use release boundaries and defer mobile, payments, GPS, and AI to later releases. |
| Reports lack business value | Medium | Define reporting requirements with finance and operations personas. |
| Architecture becomes too complex too early | Medium | Use a modular monolith first and require ADRs for major changes. |

## 13. Acceptance Criteria for Business Baseline

- Business objectives are documented.
- Stakeholders are documented.
- In-scope and out-of-scope items are documented.
- Business requirements are numbered and prioritized.
- Business rules are captured.
- KPIs are measurable.
- Assumptions, constraints, and risks are recorded.
- The SRS can be created from this BRD without guessing the business intent.

## 14. Traceability Starter Matrix

| Business Objective | Business Requirement | Future SRS Area |
| --- | --- | --- |
| BO-001 | BR-003, BR-004, BR-005 | Functional Requirements |
| BO-002 | BR-006, BR-007, BR-008 | Dashboard and Reporting |
| BO-003 | BR-006, BR-008 | Finance and Reports |
| BO-004 | BR-004, BR-009 | Compliance and Audit |
| BO-005 | BR-003, BR-004, BR-005 | Operations Workflows |
| BO-006 | BR-001, BR-002, BR-010, BR-012 | Multi-Tenancy and Architecture |

## 15. Related Documents

- TS-VSN-001: Product Vision and Strategy.
- TS-CHR-001: Project Charter.
- TS-STD-001: Documentation and Branding Standard.
- TS-ENG-001: Engineering Handbook.
- ADR-001: Multi-Tenant SaaS.
- ADR-002: Modular Monolith Architecture.
- ADR-003: Technology Stack Baseline.
