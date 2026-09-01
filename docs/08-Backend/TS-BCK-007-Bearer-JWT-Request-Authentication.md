---
Document ID: TS-BCK-007
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-007 — Bearer JWT Request Authentication

## Purpose

This document defines the request authentication filter that allows TaxiSphere APIs to authenticate users through Bearer JWT tokens.

## Request Flow

```mermaid
sequenceDiagram
    participant Client
    participant API
    participant JWT as JwtAuthenticationFilter
    participant Security as Spring Security

    Client->>API: Request with Authorization Bearer token
    API->>JWT: Inspect Authorization header
    JWT->>JWT: Validate JWT signature and issuer
    JWT->>Security: Set authenticated principal and roles
    JWT->>API: Continue request
```

## Security Behavior

- Requests without Bearer tokens continue to normal Spring Security handling.
- Requests with invalid Bearer tokens receive `401 Unauthorized`.
- Valid tokens populate the Spring Security context.
- Valid tenant-scoped tokens populate the request tenant context.
- Security and tenant contexts are cleared after every request.

## Current Compatibility

HTTP Basic remains enabled for the bootstrap platform administrator while the application authentication flow matures.
