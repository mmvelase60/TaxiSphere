---
document_id: TS-DOM-006
title: TaxiSphere Domain Events Catalogue
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
  - ADR-002
  - ADR-007
  - ADR-010
---

# TS-DOM-006 – Domain Events Catalogue

> **Status:** Approved

---

# Document Control

| Property | Value |
|----------|-------|
| Document ID | TS-DOM-006 |
| Title | Domain Events Catalogue |
| Version | 1.0.0 |
| Status | Approved |
| Owner | Enterprise Architecture |

---

# 1. Purpose

This document defines the official Domain Events used within the TaxiSphere Enterprise Mobility Platform.

Domain Events represent business-significant occurrences that have already happened.

They provide a common language between business domains and prepare TaxiSphere for future event-driven architecture while remaining fully compatible with the current Modular Monolith architecture.

---

# 2. Objectives

The Domain Events Catalogue enables:

- Loose coupling between business modules
- Business auditability
- Notification triggering
- Reporting automation
- AI analytics
- Future asynchronous processing
- Event-driven microservices

---

# 3. Event Naming Convention

Events use the format:

```
<BusinessEntity><PastTenseVerb>
```

Examples:

```
DriverRegistered
VehicleAssigned
TripStarted
PaymentProcessed
```

Events always describe something that has already occurred.

---

# 4. Event Structure Standard

Every domain event shall contain:

| Field | Description |
|--------|-------------|
| Event ID | Unique identifier |
| Event Name | Business event name |
| Aggregate Root | Aggregate publishing the event |
| Trigger | Business action |
| Event Time | UTC timestamp |
| Tenant ID | Owning tenant |
| Correlation ID | Request trace identifier |
| User ID | User responsible for the action |
| Version | Event schema version |
| Payload | Business data |

---

# 5. Platform Events

### EVT-PL-001

**Event Name**

PlatformConfigured

Aggregate Root

Platform

Trigger

Platform configuration updated.

Consumers

- Audit
- Monitoring
- Reporting

---

### EVT-PL-002

PlatformHealthChanged

Aggregate Root

Platform

Trigger

Platform health status changes.

Consumers

- Monitoring
- Alerting

---

# 6. Tenant Events

### EVT-TN-001

TenantRegistered

Aggregate Root

Tenant

Trigger

New tenant created.

Consumers

- Identity
- Billing
- Notifications

---

### EVT-TN-002

TenantUpdated

Aggregate Root

Tenant

Trigger

Tenant configuration changed.

Consumers

- Reporting
- Audit

---

# 7. Identity Events

### EVT-ID-001

UserRegistered

Aggregate Root

User

Consumers

- Notifications
- Audit

---

### EVT-ID-002

UserRoleAssigned

Aggregate Root

User

Consumers

- Security
- Audit

---

### EVT-ID-003

UserLoggedIn

Aggregate Root

User

Consumers

- Security
- Reporting

---

# 8. Driver Events

### EVT-DR-001

DriverRegistered

Aggregate Root

Driver

Consumers

- Compliance
- Reporting
- Notifications

---

### EVT-DR-002

DriverAssigned

Aggregate Root

Driver

Consumers

- Dispatch
- Reporting

---

### EVT-DR-003

DriverSuspended

Aggregate Root

Driver

Consumers

- Dispatch
- Compliance

---

### EVT-DR-004

DriverLicenceExpired

Aggregate Root

Driver

Consumers

- Compliance
- Notifications

---

# 9. Vehicle Events

### EVT-VH-001

VehicleRegistered

Aggregate Root

Vehicle

Consumers

- Fleet
- Reporting

---

### EVT-VH-002

VehicleAssigned

Aggregate Root

Vehicle

Consumers

- Dispatch

---

### EVT-VH-003

VehicleMaintenanceScheduled

Aggregate Root

Vehicle

Consumers

- Maintenance

---

### EVT-VH-004

VehicleMaintenanceCompleted

Aggregate Root

Vehicle

Consumers

- Fleet
- Reporting

---

### EVT-VH-005

VehicleRoadworthyExpired

Aggregate Root

Vehicle

Consumers

- Compliance
- Notifications

---

# 10. Route Events

### EVT-RT-001

RouteCreated

Aggregate Root

Route

Consumers

- Scheduling

---

### EVT-RT-002

RouteUpdated

Aggregate Root

Route

Consumers

- Dispatch

---

# 11. Scheduling Events

### EVT-SD-001

ScheduleCreated

Aggregate Root

Schedule

Consumers

- Dispatch

---

### EVT-SD-002

DriverDispatched

Aggregate Root

Dispatch

Consumers

- Notifications

---

# 12. Trip Events

### EVT-TR-001

TripScheduled

Aggregate Root

Trip

Consumers

- Reporting

---

### EVT-TR-002

TripStarted

Aggregate Root

Trip

Consumers

- AI
- Reporting
- Notifications

---

### EVT-TR-003

TripCompleted

Aggregate Root

Trip

Consumers

- Finance
- Reporting
- AI

---

### EVT-TR-004

TripCancelled

Aggregate Root

Trip

Consumers

- Finance
- Notifications

---

# 13. Passenger Events

### EVT-PS-001

PassengerRegistered

Aggregate Root

Passenger

Consumers

- Reporting

---

### EVT-PS-002

PassengerFeedbackSubmitted

Aggregate Root

Passenger

Consumers

- AI
- Reporting

---

# 14. Financial Events

### EVT-FN-001

PaymentProcessed

Aggregate Root

Payment

Consumers

- Reporting
- Notifications

---

### EVT-FN-002

InvoiceGenerated

Aggregate Root

Invoice

Consumers

- Notifications

---

### EVT-FN-003

RefundProcessed

Aggregate Root

Refund

Consumers

- Reporting

---

# 15. Compliance Events

### EVT-CP-001

ComplianceExpired

Aggregate Root

ComplianceItem

Consumers

- Notifications

---

### EVT-CP-002

ComplianceApproved

Aggregate Root

ComplianceItem

Consumers

- Reporting

---

# 16. Document Events

### EVT-DC-001

DocumentUploaded

Aggregate Root

Document

Consumers

- Audit
- Compliance

---

### EVT-DC-002

DocumentApproved

Aggregate Root

Document

Consumers

- Reporting

---

# 17. Notification Events

### EVT-NT-001

NotificationSent

Aggregate Root

Notification

Consumers

- Reporting

---

### EVT-NT-002

NotificationFailed

Aggregate Root

Notification

Consumers

- Monitoring

---

# 18. Event Metadata

Every published event shall include:

- Event ID
- Tenant ID
- Correlation ID
- Event Timestamp (UTC)
- Aggregate ID
- Aggregate Version
- Event Version
- Source Module
- User ID (where applicable)

---

# 19. Event Reliability

TaxiSphere shall guarantee:

- Events are immutable.
- Events are published only after successful business transactions.
- Consumers are idempotent where practical.
- Failed deliveries can be retried safely.
- Event ordering is preserved within an aggregate.

---

# 20. Future Integration

Future versions may publish events through:

- Spring Application Events (current)
- RabbitMQ
- Azure Service Bus
- Apache Kafka

The business meaning of events shall remain unchanged regardless of transport technology.

---

# 21. Governance

Domain events shall:

- Represent completed business actions.
- Never expose internal implementation details.
- Be versioned when payloads change.
- Be documented before implementation.
- Be traceable to business requirements and tests.

---

# 22. Success Criteria

The Domain Events Catalogue is successful when:

- Every significant business action has a corresponding event.
- Events are consistently named and versioned.
- Consumers remain loosely coupled.
- Events support future architectural evolution without redesign.

---

# Document Summary

The Domain Events Catalogue establishes the official business event model for TaxiSphere. It provides a consistent, versioned, and technology-independent event language that supports auditability, integration, notifications, analytics, AI capabilities, and future event-driven architecture.