---
document_id: ADR-013
title: Adopt Backend Security Bootstrap Strategy
version: 1.0.0
status: Accepted
date: 2026-09-01
decision_makers:
  - Security Architecture Office
  - Backend Engineering Office
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - ADR-004
  - TS-ARC-003
  - TS-BCK-004
---

# ADR-013: Adopt Backend Security Bootstrap Strategy

## Status

Accepted

## Context

TaxiSphere requires protected APIs, role-based authorization, password hashing, and tenant-aware identity. The full JWT authentication flow will require user registration, login, refresh token, and database-backed user loading. That is larger than the first security implementation slice.

## Decision

TaxiSphere will introduce Spring Security with a bootstrap platform administrator using stateless HTTP Basic authentication for the first security foundation.

This is a temporary bootstrap mechanism. JWT remains the target authentication strategy for the production authentication flow.

## Consequences

### Positive

- Protected endpoints can be secured immediately.
- BCrypt password hashing is available from the start.
- Platform endpoints can require `PLATFORM_ADMIN`.
- The project avoids pretending JWT is complete before the identity workflows exist.

### Negative

- HTTP Basic is not the final authentication mechanism.
- Bootstrap credentials must be handled carefully and replaced before production.
- Database-backed user loading remains a follow-up task.

## Follow-Up Work

- Implement database-backed users and roles.
- Implement JWT access tokens and refresh tokens.
- Resolve tenant context from authenticated identity.
- Add authorization integration tests.
