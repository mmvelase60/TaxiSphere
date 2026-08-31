---
document_id: TS-RTM-001
title: TaxiSphere Requirements Traceability Matrix
version: 0.1.0
status: Draft
classification: Internal Requirements
owner: Quality and Architecture Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere Requirements Traceability Matrix

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-RTM-001 |
| Version | 0.1.0 |
| Status | Draft |
| Source Documents | TS-BRD-001, TS-SRS-001, TS-USR-001, TS-USE-001 |

## Executive Summary

This matrix connects TaxiSphere business objectives and business requirements to functional requirements, user stories, use cases, and future test areas. It is the first traceability baseline for Release 0.3.0.

## 1. Objective to Requirement Traceability

| Business Objective | Business Requirements | Functional Areas |
| --- | --- | --- |
| BO-001 | BR-003, BR-004, BR-005 | Association, rank, driver, vehicle, route, dispatch |
| BO-002 | BR-006, BR-007, BR-008 | Trips, dashboard, reports |
| BO-003 | BR-006, BR-008 | Trip revenue, finance reports |
| BO-004 | BR-004, BR-009 | Driver compliance, vehicle compliance, audit |
| BO-005 | BR-003, BR-004, BR-005 | User administration and operational workflows |
| BO-006 | BR-001, BR-002, BR-010, BR-012 | Tenant management, security, architecture, deployment |

## 2. Business Requirement to Functional Requirement Traceability

| Business Requirement | Functional Requirements | User Stories | Use Cases | Future Test Area |
| --- | --- | --- | --- | --- |
| BR-001 | FR-TEN-001, FR-TEN-002, FR-TEN-003, FR-ASC-003 | US-TEN-001 | UC-001 | Tenant onboarding tests |
| BR-002 | FR-TEN-004, FR-IAM-001, FR-IAM-002, FR-IAM-003, FR-IAM-004 | US-TEN-002, US-IAM-001 | UC-002 | Authentication, authorization, tenant isolation tests |
| BR-003 | FR-IAM-005, FR-ASC-001, FR-ASC-002 | US-IAM-002, US-ASC-001 | UC-003 | User and association management tests |
| BR-004 | FR-RNK-001, FR-RNK-002, FR-DRV-001, FR-DRV-002, FR-VEH-001, FR-VEH-002, FR-RTE-001, FR-RTE-002 | US-RNK-001, US-DRV-001, US-VEH-001, US-RTE-001 | UC-004, UC-005 | CRUD, validation, compliance tests |
| BR-005 | FR-RTE-003, FR-TRP-001, FR-TRP-002 | US-TRP-001 | UC-006 | Dispatch workflow tests |
| BR-006 | FR-TRP-003, FR-TRP-004, FR-TRP-005 | US-TRP-002 | UC-007 | Trip lifecycle and revenue capture tests |
| BR-007 | FR-RNK-003, FR-VEH-004, FR-RPT-001 | US-RPT-001 | UC-008 | Dashboard tests |
| BR-008 | FR-RPT-002, FR-RPT-003, FR-RPT-004 | US-RPT-002 | UC-008 | Report generation tests |
| BR-009 | FR-AUD-001, FR-AUD-002, FR-AUD-003 | US-TEN-002, US-IAM-002, US-TRP-002 | UC-001, UC-003, UC-006, UC-007 | Audit log tests |
| BR-010 | NFR-MNT-001, NFR-SCL-001 | Future stories | Future use cases | Architecture evolution tests |
| BR-011 | TS-SRS-001, TS-USR-001, TS-USE-001, TS-RTM-001 | Documentation stories | Documentation workflows | Documentation review |
| BR-012 | NFR-DEP-001 | Deployment stories | Deployment workflows | Docker and deployment tests |

## 3. Business Rule to Requirement Traceability

| Business Rule | Requirement Coverage | Notes |
| --- | --- | --- |
| RULE-001 | FR-TEN-001, FR-TEN-002 | Tenant is the SaaS customer boundary. |
| RULE-002 | FR-IAM-004, NFR-SEC-002 | Tenant-owned data requires isolation tests. |
| RULE-003 | FR-IAM-003, FR-IAM-005 | Role assignment is required before protected access. |
| RULE-004 | FR-DRV-004 | Driver assignment constraint. |
| RULE-005 | FR-VEH-003 | Vehicle dispatch constraint. |
| RULE-006 | FR-TRP-002 | Trip aggregate integrity. |
| RULE-007 | FR-AUD-001, FR-AUD-002, FR-AUD-003 | Audit logging scope. |

## 4. Non-Functional Requirement Traceability

| NFR | Business Driver | Verification Approach |
| --- | --- | --- |
| NFR-SEC-001 | BR-002 | Security tests and access-control review. |
| NFR-SEC-002 | BR-002 | Cross-tenant integration tests. |
| NFR-PER-001 | KPI: API response time | API performance checks. |
| NFR-PER-002 | KPI: dashboard response time | Dashboard load checks. |
| NFR-SCL-001 | BO-006 | Architecture review and tenant model validation. |
| NFR-MNT-001 | BR-010, BR-011 | Module structure review. |
| NFR-OBS-001 | BR-009 | Log and audit review. |
| NFR-REL-001 | BO-001, BO-003 | Persistence, validation, and data integrity tests. |
| NFR-DEP-001 | BR-012 | Docker-based local deployment verification. |

## 5. Open Traceability Items

| Item | Reason | Target Document |
| --- | --- | --- |
| Detailed API mapping | API endpoints are not designed yet. | TS-ADS-001 |
| Database table mapping | Physical schema is not designed yet. | TS-DDS-001 |
| UI screen mapping | UI/UX specification is not created yet. | TS-UXS-001 |
| Test case mapping | Test strategy is not created yet. | TS-TST-001 |
