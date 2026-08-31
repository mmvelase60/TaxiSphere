---
document_id: TS-ADS-001
title: TaxiSphere API Design Specification
version: 0.1.0
status: Draft
classification: Internal Technical Design
owner: Backend Architecture Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere API Design Specification

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-ADS-001 |
| Version | 0.1.0 |
| Status | Draft |
| Owner | Backend Architecture Office |
| Source Documents | TS-SRS-001, TS-SAD-001, ADR-006 |

## Executive Summary

TaxiSphere exposes REST APIs for tenant administration, identity, association operations, ranks, drivers, vehicles, routes, trips, reports, and audit workflows. API contracts must be tenant-aware, role-protected, documented with OpenAPI, and consistent across modules.

## 1. API Goals

- Provide a stable contract between Angular and Spring Boot.
- Resolve tenant context server-side.
- Keep endpoints resource-oriented.
- Use consistent request validation and error responses.
- Support OpenAPI documentation from early implementation.

## 2. Base URL and Versioning

Initial API path:

```text
/api/v1
```

Breaking API changes require a new major API version or explicit migration plan.

## 3. Authentication and Tenant Context

- Protected endpoints require authentication.
- Tenant context is resolved from authenticated identity.
- Tenant IDs from client input must not override authenticated tenant context.
- Platform administration endpoints are explicitly separated from tenant-scoped endpoints.

## 4. Endpoint Catalogue

| Area | Method | Endpoint | Purpose |
| --- | --- | --- | --- |
| Health | GET | `/api/v1/health` | Verify API availability. |
| Auth | POST | `/api/v1/auth/login` | Authenticate user. |
| Auth | POST | `/api/v1/auth/refresh` | Refresh access token. |
| Tenants | POST | `/api/v1/platform/tenants` | Create tenant. |
| Tenants | PATCH | `/api/v1/platform/tenants/{tenantId}/status` | Change tenant status. |
| Users | GET | `/api/v1/users` | List tenant users. |
| Users | POST | `/api/v1/users` | Create tenant user. |
| Association | GET | `/api/v1/association` | Get current tenant association profile. |
| Association | PUT | `/api/v1/association` | Update association profile. |
| Ranks | GET | `/api/v1/ranks` | List tenant ranks. |
| Ranks | POST | `/api/v1/ranks` | Create rank. |
| Drivers | GET | `/api/v1/drivers` | List tenant drivers. |
| Drivers | POST | `/api/v1/drivers` | Create driver. |
| Vehicles | GET | `/api/v1/vehicles` | List tenant vehicles. |
| Vehicles | POST | `/api/v1/vehicles` | Create vehicle. |
| Routes | GET | `/api/v1/routes` | List tenant routes. |
| Routes | POST | `/api/v1/routes` | Create route. |
| Trips | GET | `/api/v1/trips` | List tenant trips. |
| Trips | POST | `/api/v1/trips` | Dispatch trip. |
| Trips | PATCH | `/api/v1/trips/{tripId}/status` | Update trip status. |
| Reports | GET | `/api/v1/reports/operations` | Get operational report. |
| Audit | GET | `/api/v1/audit-logs` | Search audit logs for authorized users. |

## 5. Response Standards

Successful responses should use predictable JSON objects. Collection responses should support pagination before data volume becomes large.

Example collection shape:

```json
{
  "items": [],
  "page": 0,
  "size": 20,
  "totalItems": 0,
  "totalPages": 0
}
```

## 6. Error Standards

API errors should use a consistent problem response shape:

```json
{
  "type": "https://api.taxisphere.local/problems/validation-error",
  "title": "Validation failed",
  "status": 400,
  "detail": "One or more fields are invalid",
  "instance": "/api/v1/drivers",
  "errors": []
}
```

## 7. Validation Rules

- Validate all request bodies.
- Reject missing required fields.
- Reject cross-tenant resource references.
- Reject dispatch requests with unavailable drivers or vehicles.
- Reject status transitions that violate trip lifecycle rules.

## 8. Pagination and Filtering

List endpoints should support:

- `page`
- `size`
- `sort`
- domain-specific filters such as `status`, `dateFrom`, `dateTo`, `routeId`, `driverId`, and `vehicleId`

Filters must remain tenant-scoped.

## 9. OpenAPI Requirements

The OpenAPI specification must document:

- Endpoint paths.
- Request bodies.
- Response bodies.
- Security requirements.
- Error responses.
- Required roles where practical.
- Example payloads.

## 10. Open Design Items

| Item | Decision Needed |
| --- | --- |
| Token model | Confirm access token and refresh token expiry. |
| Problem detail standard | Confirm RFC 7807 alignment during backend setup. |
| Pagination default | Confirm default page size and max page size. |
| API documentation tooling | Confirm Springdoc/OpenAPI package during implementation. |
