---
Document ID: TS-BCK-006
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-006 — Login Endpoint Foundation

## Purpose

This document defines the first database-backed authentication endpoint for the TaxiSphere API.

## Endpoint

| Method | Path | Purpose |
| --- | --- | --- |
| POST | `/api/v1/auth/login` | Validate user credentials and issue a JWT access token |

## Request

```json
{
  "username": "admin@taxisphere.local",
  "password": "password"
}
```

## Response

```json
{
  "tokenType": "Bearer",
  "accessToken": "<jwt>",
  "expiresAt": "2026-09-01T12:00:00Z"
}
```

## Security Notes

- The login endpoint is public because unauthenticated users must be able to request a token.
- Invalid credentials return a generic `401 Unauthorized` response.
- Password verification uses BCrypt.
- Issued tokens contain the user ID, tenant ID, username, and role claims.

## Current Limitation

This slice issues JWT tokens but does not yet install the Bearer token filter into the Spring Security filter chain. That will be completed in the next backend slice.
