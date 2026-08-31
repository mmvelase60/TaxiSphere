---
document_id: TS-CHR-001
title: TaxiSphere Project Charter
version: 0.2.0
status: Draft
classification: Internal Governance
owner: Product Owner
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere Project Charter

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-CHR-001 |
| Version | 0.2.0 |
| Status | Draft |
| Owner | Product Owner |
| Classification | Internal Governance |
| Related Documents | TS-VSN-001, TS-BRD-001, TS-STD-001 |

## Executive Summary

This charter authorizes TaxiSphere as an enterprise SaaS product initiative. The project will design and deliver a multi-tenant platform for taxi association operations, supported by professional documentation, controlled architecture decisions, security principles, and staged implementation.

The first delivery goal is not code. The first delivery goal is a trustworthy foundation: vision, governance, business requirements, architecture direction, templates, and traceability. This gives the future backend and frontend a clear reason to exist.

## Business Problem

Many taxi associations rely on paper records, verbal coordination, cash reconciliation, manual dispatching, and disconnected spreadsheets. These methods make it difficult to monitor operations, report accurately, manage fleet compliance, understand route performance, and scale consistently across ranks or associations.

TaxiSphere addresses this by providing one secure SaaS platform where each association operates as an isolated tenant with its own users, ranks, vehicles, drivers, routes, trips, and reports.

## Project Objectives

| ID | Objective |
| --- | --- |
| OBJ-001 | Establish a professional engineering and documentation foundation. |
| OBJ-002 | Define the business requirements that justify the platform. |
| OBJ-003 | Design a secure multi-tenant SaaS architecture. |
| OBJ-004 | Deliver a modular Java and Angular product that can evolve over time. |
| OBJ-005 | Support cloud-ready deployment through Docker first, then Kubernetes and Azure. |
| OBJ-006 | Demonstrate enterprise-grade software engineering practices in the repository. |

## Scope

### In Scope

- Product governance and documentation suite.
- Multi-tenant SaaS operating model.
- Authentication, authorization, roles, and tenant isolation.
- Association, rank, driver, vehicle, route, trip, finance, notification, and reporting capabilities.
- Architecture decision records.
- Local Docker-based development path.
- Future cloud deployment preparation.

### Out of Scope for Version 1.0

- Native mobile applications.
- Payment gateway implementation.
- Digital wallet.
- AI demand prediction.
- Live GPS tracking.
- Government and smart-city integrations.
- Multi-country localization.

## Key Stakeholders

| Stakeholder | Responsibility or Interest |
| --- | --- |
| Product Owner | Defines product direction, roadmap, and priorities. |
| Business Analyst | Converts business needs into requirements and use cases. |
| Solution Architect | Owns architecture direction and major technical decisions. |
| Association Administrator | Manages tenant-level users and association configuration. |
| Rank Manager | Oversees rank operations and dispatch performance. |
| Dispatcher | Assigns vehicles, manages queues, and starts trips. |
| Taxi Owner | Tracks vehicles, drivers, compliance, and performance. |
| Driver | Executes trips and updates operational status. |
| Finance Officer | Reviews revenue, reports, and reconciliation data. |
| Engineering Team | Builds, tests, secures, documents, and operates the platform. |

## Delivery Milestones

| Milestone | Deliverable | Success Indicator |
| --- | --- | --- |
| M0 | Foundation Kit | Repo standards, templates, ADRs, README, and governance files exist. |
| M1 | Business Analysis | Product vision, project charter, BRD, KPIs, and business scope are documented. |
| M2 | Requirements | SRS, use cases, user stories, acceptance criteria, and traceability are documented. |
| M3 | Architecture | Architecture document, C4 diagrams, security model, and deployment strategy are documented. |
| M4 | Design | Database, API, UI/UX, and test strategy are documented. |
| M5 | Implementation | Backend and frontend foundations are created and verified. |

## Success Criteria

- TaxiSphere has a documented product vision and business case.
- Business requirements are clear enough to drive an SRS.
- Major technology choices are captured in ADRs.
- The repository has clear navigation and contribution guidance.
- Core SaaS risks are identified early.
- Implementation does not begin without traceable requirements.

## Constraints

- The platform must support multi-tenancy from the beginning.
- Tenant isolation must be treated as a core security requirement.
- The initial architecture must remain practical for a solo or small-team build.
- Exact Java, Angular, and framework versions must be selected based on stable ecosystem support at implementation time.
- Documentation must remain version-controlled in Git.

## Assumptions

- Taxi associations are willing to digitize core operational workflows.
- Initial users will access the system through a web application.
- The first release focuses on administrative and operational workflows, not passenger mobile features.
- The platform will start as a modular monolith to reduce operational complexity.
- Future services may be extracted only when justified by scale, team structure, or operational needs.

## High-Level Risks

| Risk | Impact | Mitigation |
| --- | --- | --- |
| Tenant data leakage | Critical | Enforce tenant isolation in security, services, persistence, and tests. |
| Scope creep | High | Use phased releases, BRD scope, and requirements traceability. |
| Over-engineering | Medium | Start with a modular monolith and document service extraction criteria. |
| Poor user adoption | Medium | Keep operational workflows simple and validate them with use cases. |
| Weak reporting quality | Medium | Define reporting requirements and data ownership early. |

## Governance

Project work should follow the standards defined in TS-STD-001 and TS-ENG-001. Major architectural decisions require ADRs. Major features require traceability from business requirement to implementation and test evidence.

## Approval

This charter is a draft baseline for Release 0.2.0. It should be reviewed after TS-BRD-001 is accepted.
