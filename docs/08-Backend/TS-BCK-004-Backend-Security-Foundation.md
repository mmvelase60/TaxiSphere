---
document_id: TS-BCK-004
title: TaxiSphere Backend Security Foundation
version: 0.1.0
status: Draft
classification: Internal Engineering
owner: Security Architecture Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-09-01
---

# TaxiSphere Backend Security Foundation

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-BCK-004 |
| Version | 0.1.0 |
| Status | Draft |
| Owner | Security Architecture Office |
| Source Documents | TS-SAD-001, TS-ARC-003, ADR-004 |

## Executive Summary

This document records the first backend security implementation foundation. It adds Spring Security, BCrypt password encoding, bootstrap platform administrator configuration, role constants, user/role schema migration, and protected API defaults.

## 1. Security Baseline

| Area | Decision |
| --- | --- |
| Security Framework | Spring Security |
| Password Hashing | BCrypt |
| Initial Auth Mechanism | HTTP Basic bootstrap admin |
| Target Auth Mechanism | JWT in a later security slice |
| Session Model | Stateless |
| Public Endpoints | Platform health and actuator health/info |
| Protected Platform Endpoints | Require `PLATFORM_ADMIN` role |

## 2. Why HTTP Basic First

HTTP Basic is used only as a bootstrap foundation so protected APIs can be secured before the full JWT login/token workflow exists. JWT remains the target authentication model from the architecture documents.

## 3. Added Persistence Objects

The second Flyway migration creates:

- `user_account`
- `role`
- `user_account_role`

These tables prepare the system for database-backed identity and RBAC in the next implementation slice.

## 4. Configuration

Bootstrap credentials are configured through environment variables:

```text
TAXISPHERE_BOOTSTRAP_ADMIN_USERNAME
TAXISPHERE_BOOTSTRAP_ADMIN_PASSWORD
```

The default password is only for local development and must not be used in production.

## 5. Next Steps

- Replace bootstrap HTTP Basic with JWT login and refresh tokens.
- Load users and roles from the database.
- Add permission-level authorization where role checks are too broad.
- Add tenant context resolution from authenticated users.
- Add integration tests for protected endpoints.
