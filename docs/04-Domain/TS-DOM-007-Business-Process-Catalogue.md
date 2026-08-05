---
document_id: TS-DOM-007
title: TaxiSphere Business Process Catalogue
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
---

# TS-DOM-007 – Business Process Catalogue

> **Status:** Approved

---

# Document Control

| Property | Value |
|----------|-------|
| Document ID | TS-DOM-007 |
| Title | Business Process Catalogue |
| Version | 1.0.0 |
| Status | Approved |
| Owner | Enterprise Architecture |

---

# 1. Purpose

This document defines the end-to-end business processes supported by TaxiSphere.

Each process identifies:

- Business objective
- Trigger
- Participants
- Preconditions
- Process flow
- Postconditions
- Related domain events
- Business rules

The catalogue provides the operational blueprint for implementation, testing, and future workflow automation.

---

# 2. Process Classification

TaxiSphere business processes are grouped into:

- Platform Processes
- Tenant Processes
- Identity Processes
- Driver Processes
- Vehicle Processes
- Route Processes
- Scheduling & Dispatch Processes
- Trip Processes
- Passenger Processes
- Financial Processes
- Compliance Processes
- Document Processes
- Notification Processes

---

# 3. Process Template

Each process shall contain:

- Process ID
- Name
- Business Goal
- Trigger
- Actors
- Preconditions
- Main Flow
- Alternate Flow
- Business Rules
- Domain Events
- Postconditions

---

# 4. Platform Processes

## BP-PL-001 — Platform Configuration

**Goal**

Configure global platform settings.

**Actors**

- Platform Administrator

**Main Flow**

1. Administrator updates configuration.
2. Validation executes.
3. Configuration is saved.
4. Audit record is created.
5. Monitoring services are updated.

**Business Rules**

- BR-PL-001
- BR-PL-002

**Domain Events**

- PlatformConfigured

---

# 5. Tenant Processes

## BP-TN-001 — Tenant Registration

**Goal**

Register a new transport organization.

**Actors**

- Platform Administrator

**Main Flow**

1. Capture tenant details.
2. Validate uniqueness.
3. Create tenant.
4. Create default administrator.
5. Initialize tenant configuration.
6. Publish TenantRegistered event.

**Business Rules**

- BR-TN-001
- BR-TN-002

**Domain Events**

- TenantRegistered

---

# 6. Identity Processes

## BP-ID-001 — User Registration

**Actors**

- Tenant Administrator

**Main Flow**

1. Enter user details.
2. Validate email.
3. Assign role.
4. Create account.
5. Send welcome notification.

**Business Rules**

- BR-ID-001
- BR-ID-002

**Domain Events**

- UserRegistered

---

## BP-ID-002 — User Authentication

**Actors**

- User

**Main Flow**

1. Submit credentials.
2. Validate credentials.
3. Generate JWT.
4. Record audit log.
5. Return access token.

**Business Rules**

- BR-ID-001
- BR-ID-003

**Domain Events**

- UserLoggedIn

---

# 7. Driver Processes

## BP-DR-001 — Driver Registration

**Actors**

- Fleet Manager

**Main Flow**

1. Capture driver information.
2. Validate licence.
3. Validate PrDP (where required).
4. Save driver.
5. Upload supporting documents.
6. Publish DriverRegistered event.

**Business Rules**

- BR-DR-001
- BR-DR-002
- BR-DR-003

**Domain Events**

- DriverRegistered

---

## BP-DR-002 — Driver Assignment

**Actors**

- Dispatcher

**Main Flow**

1. Select driver.
2. Verify availability.
3. Verify compliance.
4. Assign vehicle.
5. Publish DriverAssigned event.

**Business Rules**

- BR-DR-004
- BR-DR-005

**Domain Events**

- DriverAssigned

---

# 8. Vehicle Processes

## BP-VH-001 — Vehicle Registration

**Actors**

- Fleet Manager

**Main Flow**

1. Capture vehicle details.
2. Validate registration.
3. Upload compliance documents.
4. Save vehicle.
5. Publish VehicleRegistered event.

**Business Rules**

- BR-VH-001
- BR-VH-002

**Domain Events**

- VehicleRegistered

---

## BP-VH-002 — Vehicle Maintenance

**Actors**

- Fleet Manager

**Main Flow**

1. Schedule maintenance.
2. Mark vehicle unavailable.
3. Record maintenance work.
4. Return vehicle to service.
5. Publish VehicleMaintenanceCompleted event.

**Business Rules**

- BR-VH-004
- BR-VH-005

**Domain Events**

- VehicleMaintenanceCompleted

---

# 9. Route Processes

## BP-RT-001 — Route Management

**Actors**

- Operations Manager

**Main Flow**

1. Create route.
2. Add stops.
3. Define operating area.
4. Save route.
5. Publish RouteCreated event.

**Business Rules**

- BR-RT-001
- BR-RT-003

**Domain Events**

- RouteCreated

---

# 10. Scheduling & Dispatch Processes

## BP-SD-001 — Trip Scheduling

**Actors**

- Dispatcher

**Main Flow**

1. Select route.
2. Select driver.
3. Select vehicle.
4. Validate availability.
5. Create schedule.
6. Publish ScheduleCreated event.

**Business Rules**

- BR-SD-001
- BR-SD-002
- BR-SD-003

**Domain Events**

- ScheduleCreated

---

# 11. Trip Processes

## BP-TR-001 — Trip Execution

**Actors**

- Driver
- Dispatcher

**Main Flow**

1. Driver starts trip.
2. System validates assignment.
3. Trip status changes to Active.
4. Monitor progress.
5. Driver completes trip.
6. Publish TripCompleted event.

**Business Rules**

- BR-TR-001
- BR-TR-002
- BR-TR-003

**Domain Events**

- TripStarted
- TripCompleted

---

# 12. Passenger Processes

## BP-PS-001 — Passenger Feedback

**Actors**

- Passenger

**Main Flow**

1. Submit feedback.
2. Validate trip reference.
3. Save feedback.
4. Notify operations.
5. Publish PassengerFeedbackSubmitted event.

**Business Rules**

- BR-PS-002

**Domain Events**

- PassengerFeedbackSubmitted

---

# 13. Financial Processes

## BP-FN-001 — Payment Processing

**Actors**

- Cashier
- Finance Officer

**Main Flow**

1. Receive payment.
2. Validate transaction.
3. Record payment.
4. Generate receipt.
5. Publish PaymentProcessed event.

**Business Rules**

- BR-FN-001
- BR-FN-004

**Domain Events**

- PaymentProcessed

---

## BP-FN-002 — Refund Processing

**Actors**

- Finance Officer

**Main Flow**

1. Validate refund request.
2. Verify original payment.
3. Approve refund.
4. Process reversal.
5. Publish RefundProcessed event.

**Business Rules**

- BR-FN-002

**Domain Events**

- RefundProcessed

---

# 14. Compliance Processes

## BP-CP-001 — Compliance Review

**Actors**

- Compliance Officer

**Main Flow**

1. Review compliance items.
2. Detect expiries.
3. Generate alerts.
4. Update compliance status.

**Business Rules**

- BR-CP-001
- BR-CP-002

**Domain Events**

- ComplianceExpired

---

# 15. Document Processes

## BP-DC-001 — Document Upload

**Actors**

- Authorized User

**Main Flow**

1. Select document.
2. Validate file.
3. Scan metadata.
4. Store securely.
5. Publish DocumentUploaded event.

**Business Rules**

- BR-DC-001
- BR-DC-002

**Domain Events**

- DocumentUploaded

---

# 16. Notification Processes

## BP-NT-001 — Notification Delivery

**Actors**

- Notification Service

**Main Flow**

1. Receive notification request.
2. Select template.
3. Deliver notification.
4. Record delivery status.
5. Retry failed deliveries if configured.

**Business Rules**

- BR-NT-001
- BR-NT-002

**Domain Events**

- NotificationSent
- NotificationFailed

---

# 17. Cross-Process Principles

All business processes shall:

- Enforce tenant isolation.
- Validate authorization.
- Apply business rules before state changes.
- Publish domain events after successful completion.
- Record audit information.
- Support idempotent retry where applicable.

---

# 18. Process Governance

Business processes shall:

- Be version controlled.
- Be traceable to requirements.
- Be mapped to automated tests.
- Be reviewed before each major release.
- Be aligned with Domain-Driven Design principles.

---

# 19. Future Workflow Automation

Future versions of TaxiSphere may automate selected business processes using:

- BPMN 2.0 Workflow Engine
- Event-driven orchestration
- AI-assisted decision support
- Human approval workflows

The business intent of each process shall remain unchanged regardless of implementation technology.

---

# 20. Success Criteria

The Business Process Catalogue is successful when:

- Every core business workflow is documented.
- Processes are traceable to business rules and domain events.
- Implementation teams can build workflows directly from the documented processes.
- Future automation can be introduced without redesigning business logic.

---

# Document Summary

The Business Process Catalogue defines the operational workflows that drive TaxiSphere. It provides a consistent and traceable description of business processes across all domains, ensuring alignment between business operations, software implementation, testing, integration, and future workflow automation.