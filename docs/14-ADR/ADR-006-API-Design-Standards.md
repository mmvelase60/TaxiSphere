---
document_id: ADR-006
title: Adopt REST API Design Standards
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
---

# ADR-006 – REST API Design Standards

> **Status:** Accepted

---

# Document Control

| Property | Value |
|----------|-------|
| ADR ID | ADR-006 |
| Title | REST API Design Standards |
| Status | Accepted |
| Version | 1.0.0 |
| Owner | Solution Architecture Office |

---

# 1. Context

TaxiSphere exposes REST APIs to Angular applications, future mobile applications, administrative portals, third-party integrations, and internal platform modules.

A consistent API design improves usability, maintainability, security, testing, and long-term evolution.

---

# 2. Problem Statement

Without API standards:

- Endpoints become inconsistent.
- Error responses differ between modules.
- Clients become difficult to maintain.
- Documentation becomes unreliable.
- Future integrations become expensive.

---

# 3. Decision

TaxiSphere shall adopt RESTful APIs using consistent resource naming, HTTP methods, versioning, standardized responses, and centralized error handling.

OpenAPI 3.x shall be the official API specification format.

---

# 4. Decision Drivers

The API strategy shall provide:

- Consistency
- Simplicity
- Predictability
- Security
- Backward compatibility
- Developer friendliness
- Maintainability

---

# 5. API Base URL

All APIs shall follow:

```
/api/v1
```

Examples:

```
/api/v1/auth

/api/v1/users

/api/v1/drivers

/api/v1/vehicles

/api/v1/routes

/api/v1/trips

/api/v1/payments
```

---

# 6. API Versioning

Versioning shall be URI-based.

Example:

```
/api/v1/drivers

/api/v2/drivers
```

Breaking changes require a new API version.

---

# 7. Resource Naming

Use plural nouns.

Correct:

```
/drivers

/vehicles

/passengers

/payments
```

Avoid verbs in endpoint names.

Incorrect:

```
/createDriver

/getDriver

/updateDriver
```

---

# 8. HTTP Methods

| Method | Purpose |
|---------|----------|
| GET | Retrieve |
| POST | Create |
| PUT | Replace |
| PATCH | Partial Update |
| DELETE | Delete (or Soft Delete) |

---

# 9. HTTP Status Codes

| Code | Meaning |
|------|----------|
| 200 | Success |
| 201 | Created |
| 204 | No Content |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 409 | Conflict |
| 422 | Validation Failed |
| 500 | Internal Server Error |

---

# 10. Request Example

```
POST /api/v1/drivers
```

```json
{
  "firstName": "John",
  "lastName": "Zulu",
  "licenseNumber": "DL123456"
}
```

---

# 11. Success Response

```json
{
  "success": true,
  "message": "Driver created successfully.",
  "data": {
    "driverId": 1001
  }
}
```

---

# 12. Error Response

```json
{
  "timestamp": "2026-08-01T10:30:15Z",
  "status": 400,
  "error": "Validation Failed",
  "message": "License number is required.",
  "path": "/api/v1/drivers"
}
```

Every error response shall follow the same structure.

---

# 13. Validation

Validation shall occur:

- Client-side (Angular)
- Server-side (Spring Boot)

Server-side validation is mandatory.

---

# 14. Pagination

Collections shall support pagination.

Example:

```
GET /api/v1/drivers?page=0&size=20
```

Optional parameters:

```
sort=lastName

direction=ASC
```

---

# 15. Filtering

Example:

```
GET /api/v1/drivers?status=ACTIVE
```

---

# 16. Searching

Example:

```
GET /api/v1/drivers?search=zulu
```

---

# 17. Sorting

Example:

```
GET /api/v1/drivers?sort=lastName,direction=ASC
```

---

# 18. Authentication

Protected endpoints require:

```
Authorization:

Bearer <JWT>
```

Authentication is defined by ADR-004.

---

# 19. Tenant Context

Tenant information shall be derived from the authenticated user's security context.

Clients must not submit tenant identifiers in requests.

---

# 20. OpenAPI

Every endpoint shall be documented using OpenAPI 3.x.

Documentation shall include:

- Description
- Parameters
- Request body
- Response body
- Status codes
- Security requirements

---

# 21. Idempotency

Operations shall follow HTTP semantics.

Examples:

- GET is idempotent.
- PUT is idempotent.
- DELETE should be idempotent.
- POST is not inherently idempotent.

Where duplicate POST requests are a concern (such as payment processing), idempotency keys may be introduced.

---

# 22. Error Handling

Global exception handling shall be implemented using Spring Boot's centralized exception mechanism.

Application errors shall never expose:

- Stack traces
- SQL queries
- Internal implementation details
- Sensitive information

---

# 23. Logging

API logs shall include:

- Request ID
- Timestamp
- Endpoint
- HTTP Method
- Response Status
- Execution Time
- User ID
- Tenant ID

Sensitive information shall not be logged.

---

# 24. Security

APIs shall enforce:

- HTTPS
- JWT validation
- RBAC
- Tenant isolation
- Input validation
- Output encoding
- Rate limiting (future enhancement)

---

# 25. Compliance

This ADR aligns with:

- TS-STD-001
- TS-ENG-001
- ADR-001
- ADR-002
- ADR-003
- ADR-004
- ADR-005

---

# Decision Summary

**Decision:** Adopt REST API standards with URI versioning, OpenAPI documentation, standardized responses, centralized error handling, and tenant-aware security.

**Status:** Accepted.

**Rationale:** A standardized REST API strategy ensures consistency, interoperability, maintainability, and scalability across TaxiSphere while providing a stable foundation for web, mobile, and third-party integrations.