# ADR-018 — Onboard Tenant With First Association Administrator

## Status

Accepted

## Context

A SaaS tenant is not useful until the taxi association has at least one administrator who can configure association-level operations.

## Decision

Tenant onboarding creates both the tenant and the first association administrator in a single transaction.

## Consequences

- Tenant onboarding becomes immediately useful after creation.
- The platform can enforce tenant ownership from the first user.
- Rollback is automatic if tenant creation or admin creation fails.
- Future onboarding workflows can add invitation emails and subscription setup.
