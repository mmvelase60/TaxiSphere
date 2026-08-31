---
document_id: TS-USR-001
title: TaxiSphere User Stories
version: 0.1.0
status: Draft
classification: Internal Requirements
owner: Product Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere User Stories

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-USR-001 |
| Version | 0.1.0 |
| Status | Draft |
| Source Documents | TS-BRD-001, TS-SRS-001 |

## Executive Summary

This document translates the initial SRS into implementation-friendly user stories. Each story describes a user goal, business value, priority, and acceptance criteria.

## 1. Platform Administration

### US-TEN-001: Create Tenant

As a Platform Administrator, I want to create a tenant for a taxi association so that the association can start using TaxiSphere as an isolated organization.

Priority: Must

Acceptance Criteria:

- The tenant has a name, status, contact details, and onboarding metadata.
- The tenant is created with an initial inactive or setup status.
- The action is audited.

### US-TEN-002: Suspend Tenant

As a Platform Administrator, I want to suspend a tenant so that blocked associations cannot access operational features.

Priority: Must

Acceptance Criteria:

- Suspended tenants cannot access protected tenant workflows.
- Existing tenant data remains preserved.
- The suspension action is audited.

## 2. Identity and Access

### US-IAM-001: Login

As a user, I want to log in securely so that I can access functionality allowed by my role.

Priority: Must

Acceptance Criteria:

- Valid credentials grant access.
- Invalid credentials are rejected.
- The system resolves user role and tenant context after authentication.

### US-IAM-002: Manage Users

As an Association Administrator, I want to create, update, deactivate, and assign roles to tenant users so that the association can control platform access.

Priority: Must

Acceptance Criteria:

- Users belong to the administrator's tenant.
- Users require at least one role before accessing protected features.
- Role changes are audited.

## 3. Association and Rank Operations

### US-ASC-001: Maintain Association Profile

As an Association Administrator, I want to maintain association profile information so that tenant records stay accurate.

Priority: Must

Acceptance Criteria:

- Authorized users can update association information.
- Users from other tenants cannot access the association profile.

### US-RNK-001: Manage Taxi Ranks

As a Rank Manager, I want to manage taxi rank records so that dispatch and reporting workflows have accurate rank data.

Priority: Must

Acceptance Criteria:

- A rank includes name, location, operating status, and tenant ownership.
- Inactive ranks are excluded from normal dispatch workflows.

## 4. Driver and Vehicle Management

### US-DRV-001: Manage Driver Profiles

As an authorized operations user, I want to manage driver profiles so that the association can track driver availability and compliance.

Priority: Must

Acceptance Criteria:

- Driver profile includes contact, licence, PDP, availability, and tenant.
- Expired or missing compliance information is visible.
- A driver cannot be assigned to two active trips.

### US-VEH-001: Manage Vehicle Profiles

As an authorized operations user, I want to manage vehicle profiles so that the association can track fleet readiness.

Priority: Must

Acceptance Criteria:

- Vehicle profile includes registration, model, capacity, owner, status, and tenant.
- Unavailable or non-compliant vehicles cannot be dispatched.

## 5. Route, Dispatch, and Trips

### US-RTE-001: Manage Routes

As an authorized operations user, I want to manage route records so that dispatchers can assign trips to approved routes.

Priority: Must

Acceptance Criteria:

- Route includes origin, destination, fare, stops, status, and tenant.
- Only active routes are available for dispatch.

### US-TRP-001: Dispatch Trip

As a Dispatcher, I want to assign a vehicle and driver to a route so that a trip can be dispatched.

Priority: Must

Acceptance Criteria:

- The selected driver is available and compliant.
- The selected vehicle is available and compliant.
- The selected route is active.
- The trip stores tenant, route, driver, vehicle, and status.

### US-TRP-002: Complete Trip

As a Dispatcher or authorized operations user, I want to complete a trip so that passenger count, timing, and revenue can be reported.

Priority: Must

Acceptance Criteria:

- Required trip completion fields are captured.
- Completed trips appear in reporting data.
- Completion is audited.

## 6. Reporting and Dashboards

### US-RPT-001: View Operational Dashboard

As an Operations Manager, I want to view operational KPIs so that I can understand rank, driver, vehicle, trip, and revenue performance.

Priority: Should

Acceptance Criteria:

- Dashboard data is tenant-scoped.
- Common dashboard views target loading under 2 seconds.
- Users only see metrics allowed by their role.

### US-RPT-002: Generate Reports

As a Finance Officer, I want to generate daily, weekly, and monthly reports so that the association can review performance and revenue.

Priority: Should

Acceptance Criteria:

- Reports can be filtered by date range.
- Reports are scoped to the user's tenant.
- Revenue and trip summaries are visible to authorized users.
