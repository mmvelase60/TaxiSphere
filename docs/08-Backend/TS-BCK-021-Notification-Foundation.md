---
Document ID: TS-BCK-021
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-021 — Notification Foundation

## Purpose

This document defines the first notification module foundation for TaxiSphere.

## Business Capability

Notifications allow TaxiSphere to record tenant-owned messages for operational, financial, security, reporting, and compliance workflows before real provider dispatch is introduced.

## Domain Model

| Entity | Purpose |
| --- | --- |
| `NotificationMessage` | Stores a tenant-owned message and delivery state |
| `NotificationChannel` | Defines delivery channel: `EMAIL`, `SMS`, `PUSH`, or `IN_APP` |
| `NotificationCategory` | Groups messages by business purpose |
| `NotificationStatus` | Tracks delivery lifecycle: `PENDING`, `SENT`, `FAILED`, or `CANCELLED` |

## Endpoints

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/notifications` | Operations and finance roles | List tenant notifications |
| GET | `/api/v1/notifications/{notificationId}` | Operations and finance roles | Get one tenant notification |
| POST | `/api/v1/notifications` | Operations and finance roles | Queue a notification record |
| POST | `/api/v1/notifications/{notificationId}/sent` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER` | Mark notification as sent |
| POST | `/api/v1/notifications/{notificationId}/failed` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER` | Mark notification as failed |
| GET | `/api/v1/notifications/summary/status` | Operations and finance roles | Return tenant notification status counts |

## Tenant Isolation

Every notification is written with the tenant ID resolved from the authenticated request context. Users can only read and update notification records belonging to their tenant.

## Design Notes

- This foundation records notification intent and status.
- It does not send real email, SMS, push, or in-app messages yet.
- Future provider integration can process `PENDING` records asynchronously.

## Future Enhancements

- RabbitMQ-backed notification dispatch
- Email provider integration
- SMS provider integration
- Push notification integration
- Retry policy and dead-letter queue
- Notification templates
- User notification preferences