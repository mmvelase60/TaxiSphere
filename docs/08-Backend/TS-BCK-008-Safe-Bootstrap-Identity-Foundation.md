---
Document ID: TS-BCK-008
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-008 — Safe Bootstrap Identity Foundation

## Purpose

This document defines TaxiSphere's safe identity bootstrap approach for development environments.

## Seeded Data

Flyway migration `V3__seed_identity_roles.sql` seeds security roles only.

| Code | Purpose |
| --- | --- |
| `PLATFORM_ADMIN` | Platform-level administration |
| `ASSOCIATION_ADMIN` | Tenant administration |
| `RANK_MANAGER` | Rank management |
| `DISPATCHER` | Dispatch operations |
| `TAXI_OWNER` | Vehicle ownership |
| `DRIVER` | Driver operations |
| `FINANCE_OFFICER` | Finance and reports |
| `OPERATIONS_MANAGER` | Operational oversight |

## Bootstrap Admin

The first platform administrator is not created by a static SQL password. Instead, `IdentityBootstrapRunner` can create one only when explicitly enabled through environment variables.

```text
TAXISPHERE_IDENTITY_BOOTSTRAP_ENABLED=true
TAXISPHERE_IDENTITY_BOOTSTRAP_EMAIL=platform-admin@taxisphere.local
TAXISPHERE_IDENTITY_BOOTSTRAP_PASSWORD=<choose-a-local-password>
```

## Security Notes

- Static administrator passwords must not be committed.
- Bootstrap creation is disabled by default.
- The password is BCrypt-hashed at application startup.
- Production environments must use a controlled onboarding process.
