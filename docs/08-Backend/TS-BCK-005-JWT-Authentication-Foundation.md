---
document_id: TS-BCK-005
title: TaxiSphere JWT Authentication Foundation
version: 0.1.0
status: Draft
classification: Internal Engineering
owner: Security Architecture Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-09-01
---

# TaxiSphere JWT Authentication Foundation

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-BCK-005 |
| Version | 0.1.0 |
| Status | Draft |
| Owner | Security Architecture Office |
| Source Documents | ADR-004, ADR-013, TS-BCK-004 |

## Executive Summary

This document records the JWT foundation for TaxiSphere. It adds JWT dependencies, token configuration, token generation, token parsing, authentication DTOs, and a unit test target for token behavior.

This slice does not yet replace the bootstrap HTTP Basic security configuration. It prepares the token infrastructure needed for the next login implementation slice.

## 1. Added Components

| Component | Purpose |
| --- | --- |
| `JwtProperties` | Binds JWT issuer, secret, and expiry configuration. |
| `AuthenticatedUser` | Represents the user identity encoded in access tokens. |
| `JwtTokenService` | Creates and parses signed JWT access tokens. |
| `LoginRequest` | Defines future login request shape. |
| `LoginResponse` | Defines future login response shape. |
| `JwtTokenServiceTests` | Validates token creation and parsing behavior. |

## 2. Configuration

JWT settings:

```text
TAXISPHERE_JWT_ISSUER
TAXISPHERE_JWT_SECRET
TAXISPHERE_JWT_ACCESS_TOKEN_MINUTES
```

The JWT secret must be replaced before any production deployment.

## 3. Current Boundary

HTTP Basic bootstrap admin remains active for now. JWT becomes active when the login endpoint, authentication filter, and database-backed user details service are added.

## 4. Next Steps

- Add database-backed user lookup.
- Add `/api/v1/auth/login`.
- Add JWT bearer token filter.
- Disable HTTP Basic once JWT login is complete.
- Add integration tests for protected endpoints.
