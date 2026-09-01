# ADR-019 — Separate Tenant Boundary From Association Profile

## Status

Accepted

## Context

TaxiSphere is a multi-tenant SaaS platform. The tenant is a technical boundary for data isolation, while the taxi association is the business organization using the platform.

## Decision

Represent taxi associations as tenant-owned domain records in `taxi_association`, separate from the platform-level `tenant` table.

## Consequences

- SaaS tenancy remains clean and technical.
- Business association data can evolve independently.
- Future tenants can support more complex organizational structures without changing the identity boundary.
