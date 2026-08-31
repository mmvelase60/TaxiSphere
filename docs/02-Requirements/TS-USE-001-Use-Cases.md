---
document_id: TS-USE-001
title: TaxiSphere Use Cases
version: 0.1.0
status: Draft
classification: Internal Requirements
owner: Business Analysis Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere Use Cases

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-USE-001 |
| Version | 0.1.0 |
| Status | Draft |
| Source Documents | TS-BRD-001, TS-SRS-001, TS-USR-001 |

## Executive Summary

This document defines the first set of TaxiSphere use cases. Each use case captures an actor goal, preconditions, main flow, alternatives, exceptions, and postconditions.

## UC-001: Onboard Tenant

| Field | Value |
| --- | --- |
| Primary Actor | Platform Administrator |
| Goal | Create a taxi association tenant. |
| Related Requirements | FR-TEN-001, FR-TEN-002 |

Preconditions:

- Platform Administrator is authenticated.
- Association onboarding information is available.

Main Flow:

1. Platform Administrator opens tenant onboarding.
2. Platform Administrator enters association identity and contact information.
3. System validates required tenant information.
4. System creates tenant in setup status.
5. System records the audit event.

Postconditions:

- Tenant exists.
- Tenant can be configured before activation.

## UC-002: Authenticate User

| Field | Value |
| --- | --- |
| Primary Actor | User |
| Goal | Access TaxiSphere securely. |
| Related Requirements | FR-IAM-001, FR-IAM-002, FR-IAM-003 |

Preconditions:

- User account exists and is active.

Main Flow:

1. User submits credentials.
2. System validates credentials.
3. System resolves role and tenant context.
4. System grants access to permitted features.

Exceptions:

- Invalid credentials are rejected.
- Suspended tenant users are blocked from tenant workflows.

Postconditions:

- Authenticated user session or token exists.
- User context includes role and tenant where applicable.

## UC-003: Manage Tenant Users

| Field | Value |
| --- | --- |
| Primary Actor | Association Administrator |
| Goal | Manage users for an association tenant. |
| Related Requirements | FR-IAM-005 |

Preconditions:

- Association Administrator is authenticated.
- Tenant is active.

Main Flow:

1. Administrator opens user management.
2. Administrator creates or updates a user.
3. Administrator assigns role information.
4. System validates tenant ownership.
5. System saves the user and records audit information.

Postconditions:

- User is available within the tenant.

## UC-004: Manage Driver

| Field | Value |
| --- | --- |
| Primary Actor | Association Administrator or Rank Manager |
| Goal | Create and maintain driver profiles. |
| Related Requirements | FR-DRV-001, FR-DRV-002, FR-DRV-003 |

Preconditions:

- Actor is authorized for driver management.

Main Flow:

1. Actor opens driver management.
2. Actor enters driver identity, contact, licence, PDP, and availability details.
3. System validates required fields.
4. System stores driver under the actor's tenant.

Exceptions:

- Users from another tenant cannot view or modify the driver.

Postconditions:

- Driver is available for compliant operational workflows.

## UC-005: Manage Vehicle

| Field | Value |
| --- | --- |
| Primary Actor | Association Administrator, Rank Manager, or Taxi Owner |
| Goal | Maintain vehicle profile and compliance information. |
| Related Requirements | FR-VEH-001, FR-VEH-002, FR-VEH-003 |

Preconditions:

- Actor is authorized for vehicle management.

Main Flow:

1. Actor opens vehicle management.
2. Actor enters registration, model, capacity, owner, status, and compliance details.
3. System validates required fields and tenant context.
4. System stores the vehicle record.

Postconditions:

- Vehicle is available for dispatch if active and compliant.

## UC-006: Dispatch Trip

| Field | Value |
| --- | --- |
| Primary Actor | Dispatcher |
| Goal | Assign a driver and vehicle to an active route. |
| Related Requirements | FR-TRP-001, FR-TRP-002, FR-TRP-003 |

Preconditions:

- Dispatcher is authenticated.
- Tenant is active.
- Route is active.
- Driver and vehicle are available and compliant.

Main Flow:

1. Dispatcher opens dispatch workflow.
2. Dispatcher selects route, rank, driver, and vehicle.
3. System validates tenant ownership and operational status.
4. System creates trip with dispatched status.
5. System records the audit event.

Exceptions:

- Non-compliant vehicles cannot be dispatched.
- Drivers already assigned to an active trip cannot be selected.

Postconditions:

- Trip exists and can progress through the trip lifecycle.

## UC-007: Complete Trip

| Field | Value |
| --- | --- |
| Primary Actor | Dispatcher or authorized operations user |
| Goal | Complete a trip and capture operational outcomes. |
| Related Requirements | FR-TRP-003, FR-TRP-004, FR-RPT-004 |

Preconditions:

- Trip exists and belongs to the user's tenant.
- Trip is in a completable state.

Main Flow:

1. Actor opens active trip.
2. Actor enters arrival time, passenger count, revenue, and completion notes where applicable.
3. System validates required completion data.
4. System marks trip as completed.
5. System makes trip data available for reports.

Postconditions:

- Trip is completed.
- Reporting data is updated.

## UC-008: View Dashboard and Reports

| Field | Value |
| --- | --- |
| Primary Actor | Operations Manager or Finance Officer |
| Goal | Review tenant operational and financial performance. |
| Related Requirements | FR-RPT-001, FR-RPT-002, FR-RPT-003, FR-RPT-004 |

Preconditions:

- Actor is authenticated and authorized.

Main Flow:

1. Actor opens dashboard or reports.
2. Actor selects date range or filter values.
3. System retrieves tenant-scoped data.
4. System displays KPI, trip, revenue, route, driver, or vehicle information.

Postconditions:

- Actor can make operational or financial decisions from the displayed data.
