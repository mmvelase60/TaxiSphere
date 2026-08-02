---
document_id: ADR-010
title: Adopt the Notification & Communication Strategy
version: 1.0.0
status: Accepted
date: 2026-08-01
decision_makers:
  - Solution Architecture Office
  - Product Owner
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - TS-STD-001
  - TS-ENG-001
  - ADR-001
  - ADR-002
  - ADR-003
  - ADR-004
  - ADR-005
  - ADR-006
  - ADR-007
  - ADR-008
  - ADR-009
---

# ADR-010 – Notification & Communication Strategy

> **Status:** Accepted

---

# Document Control

| Property | Value |
|----------|-------|
| ADR ID | ADR-010 |
| Title | Notification & Communication Strategy |
| Version | 1.0.0 |
| Status | Accepted |
| Product | TaxiSphere Enterprise Mobility Platform |
| Owner | Solution Architecture Office |

---

# 1. Context

TaxiSphere supports multiple transport organizations and requires reliable communication with drivers, passengers, dispatchers, administrators, and platform operators.

Notifications are essential for operational efficiency, security, compliance, and customer experience.

---

# 2. Problem Statement

Without a centralized notification strategy:

- Communication becomes inconsistent.
- Duplicate notification logic appears across modules.
- Message delivery cannot be monitored.
- Templates become difficult to maintain.
- Future communication channels become harder to integrate.

TaxiSphere requires a unified communication platform.

---

# 3. Decision

TaxiSphere shall implement a centralized Notification Service responsible for all outbound communications.

Version 1 supports:

- Email
- In-App Notifications

Future versions may support:

- SMS
- Push Notifications
- WhatsApp
- Microsoft Teams
- Voice Notifications

---

# 4. Decision Drivers

The notification platform shall provide:

- Reliability
- Scalability
- Template management
- Tenant branding
- Delivery tracking
- Security
- Extensibility

---

# 5. Notification Architecture

```
Business Module

↓

Notification Service

↓

Template Engine

↓

Channel Provider

↓

Recipient
```

Business modules shall never communicate directly with notification providers.

---

# 6. Supported Channels

Version 1

- Email
- In-App Notifications

Future

- SMS
- Mobile Push
- WhatsApp
- Voice
- Microsoft Teams
- Slack
- Webhooks

---

# 7. Notification Categories

Operational

- Trip Assignment
- Route Changes
- Schedule Updates
- Vehicle Allocation

Security

- Login Alerts
- Password Reset
- MFA Verification
- Account Lockout

Financial

- Payment Confirmation
- Refund Notification
- Invoice Available

Compliance

- Expiring Driver Licence
- Expiring PrDP
- Vehicle Roadworthy Expiry
- Insurance Renewal Reminder

Administrative

- System Announcements
- Maintenance Windows
- Tenant Messages

---

# 8. Email Templates

Email content shall use centralized templates.

Each template shall support:

- Dynamic placeholders
- Localization
- Tenant branding
- HTML
- Plain text

Example placeholders:

```
{{firstName}}

{{tripNumber}}

{{vehicleRegistration}}

{{tenantName}}

{{supportEmail}}
```

---

# 9. Tenant Branding

Each tenant may configure:

- Logo
- Primary colour
- Sender name
- Reply-to address
- Footer
- Contact information

Notifications shall automatically apply tenant branding.

---

# 10. Delivery Status

Each notification shall record:

- Notification ID
- Tenant ID
- Recipient
- Channel
- Template
- Created Time
- Sent Time
- Delivery Status
- Failure Reason (if applicable)

---

# 11. Retry Policy

Temporary failures shall be retried automatically.

Suggested retry intervals:

- 1 minute
- 5 minutes
- 15 minutes
- 1 hour

Permanent failures shall be marked and logged for investigation.

---

# 12. User Preferences

Users may configure notification preferences.

Examples:

- Receive email notifications
- Receive SMS notifications
- Receive marketing messages
- Receive operational alerts

Security notifications cannot be disabled where required by policy.

---

# 13. Security

The notification platform shall enforce:

- Authentication
- Authorization
- Tenant isolation
- Secure transport (TLS)
- Audit logging

Sensitive information shall not be transmitted unless explicitly required and appropriately protected.

---

# 14. Monitoring

The platform shall monitor:

- Delivery success rate
- Delivery failures
- Retry count
- Queue size
- Processing time
- Provider availability

These metrics integrate with ADR-007.

---

# 15. Notification Queue

Future releases may process notifications asynchronously using a messaging platform.

Potential technologies include:

- RabbitMQ
- Azure Service Bus
- Apache Kafka

Technology adoption requires a dedicated ADR.

---

# 16. Future Providers

Potential integrations include:

- Azure Communication Services
- Twilio
- SendGrid
- Amazon SES
- Firebase Cloud Messaging (FCM)
- Apple Push Notification Service (APNs)

Provider selection requires a dedicated ADR.

---

# 17. Risks

| Risk | Mitigation |
|------|------------|
| Provider outage | Retry policy and provider abstraction |
| Duplicate notifications | Idempotency and unique message identifiers |
| Spam or abuse | Rate limiting and user preferences |
| Delivery failures | Monitoring, alerting and retry mechanisms |

---

# 18. Consequences

## Positive

- Consistent communication
- Centralized template management
- Better user experience
- Easier maintenance
- Multi-channel support
- Future extensibility

## Negative

- Additional service complexity
- Operational monitoring required
- Dependency on communication providers

---

# 19. Compliance

This decision complies with:

- TS-STD-001 – Documentation & Engineering Standards
- TS-ENG-001 – Engineering Handbook
- ADR-001 – Multi-Tenant SaaS Architecture
- ADR-002 – Modular Monolith Architecture
- ADR-003 – Technology Stack Baseline
- ADR-004 – Authentication & Authorization Strategy
- ADR-005 – Database Strategy & Tenant Data Isolation
- ADR-006 – REST API Design Standards
- ADR-007 – Logging, Monitoring & Observability Strategy
- ADR-008 – Caching Strategy
- ADR-009 – File Storage & Document Management Strategy

---

# Decision Summary

**Decision:** Adopt a centralized Notification & Communication Strategy with a Notification Service responsible for email and in-app notifications in Version 1, while providing an extensible architecture for future SMS, push notifications, WhatsApp, voice, and third-party messaging providers.

**Status:** Accepted.

**Rationale:** A centralized notification platform provides consistent communication, tenant branding, reliable delivery, operational visibility, and a scalable foundation for future communication channels without coupling business modules to specific providers.