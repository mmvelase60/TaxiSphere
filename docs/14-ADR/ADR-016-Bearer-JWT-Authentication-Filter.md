# ADR-016 — Add Bearer JWT Authentication Filter

## Status

Accepted

## Context

TaxiSphere can issue JWT access tokens, but secured endpoints must also validate those tokens on incoming requests.

## Decision

Introduce a Spring `OncePerRequestFilter` that reads Bearer tokens from the `Authorization` header, validates them through `JwtTokenService`, sets Spring Security authorities, and resolves tenant context for tenant-scoped users.

## Consequences

- API requests can now authenticate with JWT access tokens.
- Tenant context is available during authenticated requests.
- Invalid tokens fail early with `401 Unauthorized`.
- The bootstrap HTTP Basic administrator remains available during early platform setup.
