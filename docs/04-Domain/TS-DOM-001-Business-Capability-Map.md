---
document_id: TS-DOM-001
title: TaxiSphere Business Capability Map
version: 1.0.0
status: Approved
date: 2026-08-02
owner: Enterprise Architecture
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - TS-STD-001
  - TS-ENG-001
  - ADR-001
  - ADR-011
---

# TS-DOM-001 – TaxiSphere Business Capability Map

> **Status:** Approved

---

# Document Control

| Property | Value |
|----------|-------|
| Document ID | TS-DOM-001 |
| Title | Business Capability Map |
| Version | 1.0.0 |
| Status | Approved |
| Owner | Enterprise Architecture |

---

# 1. Purpose

This document defines the complete set of business capabilities provided by the TaxiSphere Enterprise Mobility Platform.

The Business Capability Map serves as the foundation for:

- Business Architecture
- Domain-Driven Design (DDD)
- Requirements Engineering
- Database Design
- REST API Design
- User Interface Design
- Security
- Reporting
- Artificial Intelligence
- Future Platform Evolution

Every software feature developed for TaxiSphere shall align with one or more business capabilities defined in this document.

---

# 2. Vision

TaxiSphere aims to become Africa's leading cloud-native enterprise mobility platform for managing taxi associations, transport operators, drivers, fleets, passengers, routes, compliance, and intelligent transport services.

The platform shall support multiple independent organizations through a secure, scalable, multi-tenant Software-as-a-Service (SaaS) architecture.

---

# 3. Business Capability Hierarchy

```
TaxiSphere Enterprise Mobility Platform

│

├── Platform Management

├── Tenant Management

├── Identity & Security

├── Fleet Operations

├── Driver Operations

├── Vehicle Operations

├── Route Management

├── Scheduling & Dispatch

├── Passenger Services

├── Trip Management

├── Financial Management

├── Compliance Management

├── Document Management

├── Notifications & Communication

├── Reporting & Analytics

├── Platform Administration

└── Artificial Intelligence (Future)
```

---

# 4. Capability Overview

| Capability | Description |
|------------|-------------|
| Platform Management | Core platform administration and configuration |
| Tenant Management | Management of transport organizations and tenants |
| Identity & Security | Authentication, authorization, users, roles and permissions |
| Fleet Operations | Fleet administration and vehicle allocation |
| Driver Operations | Driver lifecycle and licensing |
| Vehicle Operations | Vehicle registration, maintenance and inspections |
| Route Management | Routes, stops and operating corridors |
| Scheduling & Dispatch | Trip scheduling and dispatch operations |
| Passenger Services | Passenger registration and support |
| Trip Management | Daily transport operations and trip execution |
| Financial Management | Fares, payments, billing and financial records |
| Compliance Management | Regulatory compliance and audits |
| Document Management | Secure document storage and lifecycle management |
| Notifications & Communication | Email, in-app messaging and future communication channels |
| Reporting & Analytics | Operational dashboards and business intelligence |
| Platform Administration | Platform configuration and operational governance |
| Artificial Intelligence | Intelligent transport and predictive analytics (future) |

---

# 5. Platform Management

Provides platform-wide operational capabilities.

Includes:

- Platform Configuration
- Environment Management
- System Health
- Monitoring
- Audit Logging
- Feature Management
- Global Settings

---

# 6. Tenant Management

Supports independent transport organizations.

Includes:

- Tenant Registration
- Tenant Configuration
- Subscription Management
- Tenant Branding
- Regional Settings
- Business Profiles

---

# 7. Identity & Security

Provides secure access to the platform.

Includes:

- User Management
- Authentication
- Authorization
- Role Management
- Permission Management
- Multi-Factor Authentication (Future)
- Password Policies
- Session Management

---

# 8. Fleet Operations

Manages transport fleets.

Includes:

- Fleet Registration
- Fleet Assignment
- Fleet Status
- Fleet Availability
- Fleet Performance

---

# 9. Driver Operations

Manages the complete driver lifecycle.

Includes:

- Driver Registration
- Driver Profiles
- Licence Management
- Professional Driving Permit (PrDP)
- Driver Assignment
- Driver Performance
- Driver Availability

---

# 10. Vehicle Operations

Manages transport vehicles.

Includes:

- Vehicle Registration
- Vehicle Documents
- Vehicle Maintenance
- Roadworthy Certificates
- Insurance
- Inspection History
- Vehicle Availability

---

# 11. Route Management

Defines transport routes.

Includes:

- Route Creation
- Stops
- Route Mapping
- Fare Zones
- Distance Management
- Operating Areas

---

# 12. Scheduling & Dispatch

Coordinates daily operations.

Includes:

- Driver Scheduling
- Vehicle Scheduling
- Dispatch
- Shift Allocation
- Daily Operations
- Route Assignments

---

# 13. Passenger Services

Provides passenger-facing capabilities.

Includes:

- Passenger Profiles
- Passenger Support
- Trip History
- Feedback
- Lost Property
- Service Requests

---

# 14. Trip Management

Manages operational transport services.

Includes:

- Trip Planning
- Trip Execution
- Live Trip Monitoring
- Trip History
- Trip Status
- Trip Completion

---

# 15. Financial Management

Supports commercial operations.

Includes:

- Fare Management
- Payments
- Billing
- Invoicing
- Refunds
- Financial Reporting

---

# 16. Compliance Management

Supports regulatory compliance.

Includes:

- Driver Compliance
- Vehicle Compliance
- Regulatory Reporting
- Audit Preparation
- Compliance Monitoring
- Expiry Tracking

---

# 17. Document Management

Supports secure document storage.

Includes:

- Upload
- Download
- Version Tracking
- Metadata
- Document Approval
- Retention
- Archival

---

# 18. Notifications & Communication

Supports platform communications.

Includes:

- Email
- In-App Notifications
- Alerts
- Announcements
- Templates
- Delivery Tracking

Future versions may support SMS, push notifications and messaging integrations.

---

# 19. Reporting & Analytics

Supports operational intelligence.

Includes:

- Dashboards
- Operational Reports
- Financial Reports
- Compliance Reports
- KPIs
- Trend Analysis

---

# 20. Platform Administration

Supports operational governance.

Includes:

- Configuration
- User Administration
- System Parameters
- Audit Review
- Platform Monitoring
- Maintenance

---

# 21. Artificial Intelligence (Future)

Future intelligent capabilities include:

- Driver Behaviour Analysis
- Predictive Vehicle Maintenance
- Route Optimisation
- Demand Forecasting
- Fraud Detection
- Smart Dispatch
- AI Assistant
- Business Recommendations

---

# 22. Capability Relationships

```
Platform Management
        │
        ▼
Tenant Management
        │
        ▼
Identity & Security
        │
        ▼
Fleet + Driver + Vehicle
        │
        ▼
Routes
        │
        ▼
Scheduling & Dispatch
        │
        ▼
Trips
        │
        ▼
Passengers
        │
        ▼
Payments
        │
        ▼
Reporting

Compliance, Documents and Notifications support all business capabilities.
```

---

# 23. Future Capability Expansion

The Business Capability Map is designed to support future expansion without major architectural changes.

Potential future capabilities include:

- Mobile Driver Application
- Passenger Mobile Application
- Taxi Rank Operations
- Cashless Payments
- Government Integration
- Banking Integration
- Insurance Integration
- Fleet IoT Integration
- AI Decision Support
- Cross-Border Transport Management

---

# 24. Governance

Business capabilities shall:

- Align with Domain-Driven Design principles.
- Maintain clear ownership.
- Support tenant isolation.
- Be independently testable.
- Follow established architecture standards.
- Preserve backward compatibility where practical.

---

# 25. Success Criteria

The Business Capability Map is considered successful when:

- Every business requirement maps to a defined capability.
- Every bounded context aligns with a business capability.
- Every API, database schema and UI feature can be traced back to a capability.
- Future capabilities can be introduced without disrupting existing domains.

---

# Document Summary

This Business Capability Map establishes the strategic business architecture for TaxiSphere. It defines the platform's core business capabilities, aligns them with Domain-Driven Design principles, and provides the foundation for requirements, implementation, governance and future platform growth.