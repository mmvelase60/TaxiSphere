# ADR-015 — Introduce Database-Backed Login Endpoint

## Status

Accepted

## Context

TaxiSphere needs to move from bootstrap HTTP Basic authentication toward normal JWT-based application authentication.

## Decision

Add a `/api/v1/auth/login` endpoint backed by the `user_account` and `security_role` database model. The endpoint validates BCrypt password hashes and returns a signed JWT access token.

## Consequences

- Application users can authenticate through a REST endpoint.
- The security model now has a clear path from database identity to JWT claims.
- A Bearer token filter is still required before JWTs protect secured endpoints.
