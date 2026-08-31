---
document_id: ADR-012
title: Adopt Initial Backend Runtime Baseline
version: 1.0.0
status: Accepted
date: 2026-08-31
decision_makers:
  - Backend Engineering Office
  - Solution Architecture Office
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - ADR-003
  - TS-BCK-001
  - TS-SAD-001
---

# ADR-012: Adopt Initial Backend Runtime Baseline

## Status

Accepted

## Context

ADR-003 states that TaxiSphere should use the latest stable Java release supported by the selected Spring Boot version at implementation time. During the first backend foundation pass, the local development machine had Java 17 installed and Maven was not installed on the system path.

The backend foundation needed to be practical, build-ready, and aligned with a stable Spring Boot baseline without blocking the repository on local toolchain upgrades.

## Decision

TaxiSphere will start the backend foundation with:

| Area | Decision |
| --- | --- |
| Java | Java 17 |
| Spring Boot | 3.3.5 |
| Build Tool | Maven |
| Application Path | `backend/taxisphere-api` |
| Package Root | `com.spheretech.taxisphere` |

This is an implementation baseline for the first backend scaffold, not a permanent lock on Java 17.

## Rationale

- Java 17 is installed on the current development machine.
- Spring Boot 3.3.x supports Java 17 and is stable for enterprise backend development.
- The scaffold can be upgraded later after the local toolchain is aligned.
- Starting with a working foundation is better than waiting for a future runtime decision.

## Consequences

### Positive

- Backend scaffolding can begin immediately.
- The project remains compatible with the available local Java runtime.
- Spring Boot support is stable and mature.
- Future upgrade path remains open.

### Negative

- The backend does not yet use a newer Java release.
- Maven installation or Maven wrapper setup is still required before build verification.

## Future Review

This ADR should be reviewed when:

- Maven or a Maven wrapper is added.
- The project upgrades to a newer Spring Boot version.
- The local development machine installs a newer Java LTS.
- CI/CD is configured for backend builds.
