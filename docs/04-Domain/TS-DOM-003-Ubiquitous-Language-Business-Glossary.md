---
document_id: TS-DOM-003
title: TaxiSphere Ubiquitous Language & Business Glossary
version: 1.0.0
status: Approved
date: 2026-08-02
owner: Enterprise Architecture
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - TS-DOM-001
  - TS-DOM-002
  - ADR-011
---

# TS-DOM-003 – Ubiquitous Language & Business Glossary

> **Status:** Approved

---

# Document Control

| Property | Value |
|----------|-------|
| Document ID | TS-DOM-003 |
| Title | Ubiquitous Language & Business Glossary |
| Version | 1.0.0 |
| Status | Approved |
| Owner | Enterprise Architecture |

---

# 1. Purpose

This document establishes the official business vocabulary for TaxiSphere.

Its purpose is to ensure that business stakeholders, developers, testers, architects, support teams, and customers use the same terminology consistently.

The glossary forms the foundation of TaxiSphere's Domain-Driven Design (DDD) approach.

---

# 2. Guiding Principles

The ubiquitous language shall:

- Use business terminology rather than technical jargon.
- Be consistent across documentation, APIs, databases, UI, and source code.
- Avoid duplicate or conflicting definitions.
- Be reviewed whenever new business capabilities are introduced.

---

# 3. Core Business Terms

## Platform

The complete TaxiSphere Enterprise Mobility Platform providing mobility services to multiple transport organizations.

---

## Tenant

An independent organization using TaxiSphere.

Examples:

- Taxi Association
- Bus Company
- Shuttle Operator
- Corporate Fleet

Each tenant has isolated users, data, configuration, branding, and operational records.

---

## Taxi Association

A transport organization responsible for managing drivers, vehicles, routes, schedules, and operational policies.

A Taxi Association is one example of a Tenant.

---

## User

A person who can authenticate and access TaxiSphere.

Examples include:

- Administrator
- Dispatcher
- Driver
- Finance Officer
- Operations Manager

---

## Role

A collection of permissions assigned to users.

Examples:

- Platform Administrator
- Tenant Administrator
- Dispatcher
- Fleet Manager
- Finance Officer

---

## Permission

A specific authorization allowing a user to perform an action.

Example:

```
driver.create

driver.update

trip.view

payment.approve
```

---

# 4. Fleet Domain

## Fleet

A managed collection of vehicles operated by a tenant.

---

## Vehicle

A registered transport vehicle used to provide passenger transport services.

---

## Vehicle Assignment

The allocation of a vehicle to a driver, route, shift, or operational activity.

---

## Vehicle Inspection

A scheduled or completed inspection verifying the safety and operational readiness of a vehicle.

---

## Maintenance Record

A record describing servicing, repairs, inspections, or maintenance activities performed on a vehicle.

---

# 5. Driver Domain

## Driver

A licensed individual authorised to operate a transport vehicle.

---

## Driver Licence

The legal licence permitting a person to drive.

---

## Professional Driving Permit (PrDP)

A regulatory permit required for professional public transport drivers.

---

## Driver Assignment

The allocation of a driver to a vehicle, route, or shift.

---

## Driver Availability

The operational status indicating whether a driver is available for assignment.

---

# 6. Route Domain

## Route

A predefined transport path connecting multiple stops.

---

## Stop

A designated location where passengers board or leave a vehicle.

---

## Taxi Rank

A recognised passenger pickup and drop-off location.

Taxi ranks may serve one or multiple routes.

---

## Operating Area

The geographical region in which a tenant is authorised to operate.

---

# 7. Operations Domain

## Shift

A scheduled working period assigned to a driver.

---

## Dispatch

The process of assigning drivers and vehicles to operational work.

---

## Schedule

A planned timetable defining operational activities.

---

## Trip

A completed or active transport operation performed by a driver using a vehicle along a route.

A Trip is the primary operational record within TaxiSphere.

---

## Trip Status

The lifecycle stage of a trip.

Examples:

- Planned
- Scheduled
- Active
- Delayed
- Completed
- Cancelled

---

# 8. Passenger Domain

## Passenger

A person receiving transport services.

---

## Passenger Profile

Information associated with a registered passenger.

---

## Passenger Feedback

Feedback submitted by passengers regarding transport services.

---

# 9. Financial Domain

## Fare

The amount charged for transport services.

---

## Payment

A financial transaction settling transport charges.

---

## Invoice

A document requesting payment for services.

---

## Refund

The reversal of a previously completed payment.

---

# 10. Compliance Domain

## Compliance

The process of ensuring legal and regulatory obligations are satisfied.

---

## Compliance Item

Any document, licence, permit, certificate, or requirement that must remain valid.

---

## Expiry

The date after which a compliance item is no longer valid.

---

# 11. Document Domain

## Document

A digital file managed by TaxiSphere.

Examples:

- Driver Licence
- PrDP
- Insurance Certificate
- Roadworthy Certificate
- Vehicle Registration

---

## Metadata

Information describing a stored document without containing the document itself.

---

# 12. Notification Domain

## Notification

A communication generated by TaxiSphere.

Examples:

- Email
- In-App Notification
- SMS (Future)
- Push Notification (Future)

---

## Template

A reusable message format used when generating notifications.

---

# 13. Reporting Domain

## Dashboard

A visual summary of operational information.

---

## KPI (Key Performance Indicator)

A measurable value used to evaluate operational performance.

Examples:

- Active Drivers
- Daily Trips
- Revenue
- Vehicle Utilisation

---

## Report

A structured presentation of business information generated by the platform.

---

# 14. Security Domain

## Authentication

The process of verifying the identity of a user.

---

## Authorization

The process of determining what an authenticated user is allowed to do.

---

## Session

The authenticated interaction between a user and TaxiSphere.

---

## Audit Log

A permanent record of significant business or security events.

---

# 15. Artificial Intelligence Domain (Future)

## AI Recommendation

A system-generated suggestion based on operational data.

---

## Demand Forecast

A prediction of future passenger demand.

---

## Route Optimisation

The process of recommending improved transport routes using operational data.

---

## Predictive Maintenance

The use of historical maintenance and operational data to estimate future vehicle servicing requirements.

---

# 16. Naming Standards

Business terminology shall:

- Use singular nouns where appropriate.
- Avoid abbreviations unless widely recognised.
- Maintain consistent spelling.
- Align with API names, database entities, and UI labels.

Examples:

| Correct | Avoid |
|----------|-------|
| Driver | Drivers |
| Vehicle | Car |
| Trip | Journey |
| Tenant | Client |
| Passenger | Customer |
| Taxi Rank | Station |

---

# 17. Governance

The glossary shall be:

- Maintained by Enterprise Architecture.
- Reviewed during domain modelling.
- Updated whenever new business capabilities are introduced.
- Used consistently throughout TaxiSphere documentation and source code.

---

# 18. Success Criteria

This glossary is considered successful when:

- Business stakeholders use consistent terminology.
- Developers and testers use identical definitions.
- APIs, UI labels, database entities, and documentation share the same language.
- Ambiguous terminology is eliminated across the platform.

---

# Document Summary

This document defines the official ubiquitous language for TaxiSphere. It provides a shared business vocabulary that aligns business stakeholders and technical teams, ensuring consistency across architecture, documentation, implementation, testing, and future platform evolution.