---
document_id: TS-BCK-001
title: TaxiSphere Backend Foundation
version: 0.1.0
status: Draft
classification: Internal Engineering
owner: Backend Engineering Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere Backend Foundation

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-BCK-001 |
| Version | 0.1.0 |
| Status | Draft |
| Owner | Backend Engineering Office |
| Source Documents | TS-SAD-001, TS-ADS-001, TS-TST-001, ADR-003 |

## Executive Summary

This document records the first backend implementation foundation for TaxiSphere. The backend starts as a Spring Boot modular monolith with a health endpoint, baseline package structure, validation support, Actuator health checks, and a global API exception handler.

## 1. Implementation Baseline

| Area | Decision |
| --- | --- |
| Runtime | Java 17 for local compatibility |
| Framework | Spring Boot 3.3.5 |
| Build Tool | Maven |
| Package Root | `com.spheretech.taxisphere` |
| Deployment Unit | Single backend application |
| Architecture Style | Modular monolith |

## 2. Initial Project Location

```text
backend/taxisphere-api
```

## 3. Initial Package Structure

```text
com.spheretech.taxisphere
  platform.api
  shared.api
```

Future modules will follow the bounded contexts defined in TS-SAD-001 and TS-DOM-002.

## 4. Initial Endpoint

```text
GET /api/v1/platform/health
```

This endpoint returns platform status, application name, version, and timestamp.

## 5. Verification Status

Build verification is pending because Maven is not installed on the current machine. The source has been scaffolded so that verification can run once Maven or a Maven wrapper is available.

## 6. Next Backend Steps

- Add Maven wrapper or install Maven.
- Run `mvn test`.
- Add base security configuration.
- Add tenant context model.
- Add first tenant management module.
- Add database migration tooling.
