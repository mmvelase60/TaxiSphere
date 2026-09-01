# ADR-031 — Create Derived Compliance Overview

## Status

Accepted

## Context

Driver and vehicle records already contain key expiry fields for licences, PDPs, roadworthy certificates, and insurance policies. TaxiSphere needs compliance visibility before introducing complex inspection and document-verification workflows.

## Decision

Create a tenant-scoped compliance overview endpoint that derives expired and expiring compliance metrics from existing driver and vehicle fields.

## Consequences

- Compliance value is delivered without adding unnecessary workflow tables too early.
- The implementation remains simple and tenant-safe.
- Future compliance cases, inspections, uploads, and automated reminders can build on this foundation.