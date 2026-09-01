---
document_id: ADR-014
title: Adopt JWT Token Foundation
version: 1.0.0
status: Accepted
date: 2026-09-01
decision_makers:
  - Security Architecture Office
  - Backend Engineering Office
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - ADR-004
  - ADR-013
  - TS-BCK-005
---

# ADR-014: Adopt JWT Token Foundation

## Status

Accepted

## Context

TaxiSphere's target authentication model is JWT-based authentication with tenant-aware identity and role information. The platform currently has a bootstrap HTTP Basic administrator so early platform endpoints can be protected before the full login flow exists.

## Decision

TaxiSphere will add a JWT token foundation using the JJWT library. The token service will sign access tokens, include user identity, tenant identity, and roles, and validate issuer and signature during parsing.

This decision introduces JWT infrastructure without yet replacing the bootstrap authentication mechanism.

## Consequences

### Positive

- JWT token logic can be tested independently.
- Future login implementation has a clear token service.
- Tenant and role claims are defined early.
- Bootstrap security can be replaced in a controlled slice.

### Negative

- JWT is not fully active until a bearer filter and login endpoint are implemented.
- Secret management must be improved before production.
- Token revocation and refresh token strategy remain future work.

## Follow-Up Work

- Implement login endpoint.
- Implement database-backed user authentication.
- Implement bearer token filter.
- Define refresh token persistence.
- Add tenant context resolution from JWT claims.
