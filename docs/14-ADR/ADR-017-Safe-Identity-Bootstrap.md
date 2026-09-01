# ADR-017 — Use Safe Opt-In Identity Bootstrap

## Status

Accepted

## Context

TaxiSphere needs local identity data for development, but committing a known administrator password would be unsafe and could leak into non-local environments.

## Decision

Seed only security roles through Flyway. Create the first platform administrator through an opt-in application runner that requires explicit environment variables and hashes the password at runtime.

## Consequences

- Roles are always available after migration.
- No static administrator password is stored in source control.
- Local developers can still create a first admin predictably.
- Production administrator provisioning remains a separate controlled process.
