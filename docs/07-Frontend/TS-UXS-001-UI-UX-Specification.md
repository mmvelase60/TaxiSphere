---
document_id: TS-UXS-001
title: TaxiSphere UI UX Specification
version: 0.1.0
status: Draft
classification: Internal Product Design
owner: UX and Frontend Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere UI UX Specification

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-UXS-001 |
| Version | 0.1.0 |
| Status | Draft |
| Owner | UX and Frontend Office |
| Source Documents | TS-SRS-001, TS-USR-001, TS-SAD-001 |

## Executive Summary

TaxiSphere is an operations platform. Its interface should be calm, efficient, readable, and optimized for repeated daily workflows. The UI must help dispatchers, managers, administrators, and finance users act quickly without hiding important operational state.

## 1. UX Goals

- Make common operational workflows fast.
- Keep tenant and role context clear.
- Show operational status without visual clutter.
- Support table-heavy management screens with strong filtering.
- Use consistent navigation across modules.
- Make error and validation messages useful.

## 2. Product Navigation

Primary navigation areas:

- Dashboard.
- Platform Administration.
- Association.
- Ranks.
- Drivers.
- Vehicles.
- Routes.
- Dispatch.
- Trips.
- Reports.
- Audit Logs.
- Settings.

Navigation visibility depends on role and tenant context.

## 3. Screen Catalogue

| Screen | Primary Users | Purpose |
| --- | --- | --- |
| Login | All users | Secure platform access. |
| Dashboard | Managers, finance, operations | View KPIs and operational health. |
| Tenant Management | Platform Administrator | Create and manage tenants. |
| User Management | Association Administrator | Manage tenant users and roles. |
| Association Profile | Association Administrator | Maintain association details. |
| Rank Management | Rank Manager | Manage taxi ranks and queue visibility. |
| Driver Management | Rank Manager, Association Administrator | Manage driver records and compliance. |
| Vehicle Management | Taxi Owner, Rank Manager | Manage vehicles and compliance. |
| Route Management | Operations users | Manage routes, fares, and stops. |
| Dispatch Console | Dispatcher | Assign vehicle and driver to route. |
| Trip Details | Dispatcher, operations | View and update trip lifecycle. |
| Reports | Finance Officer, Operations Manager | Review operational and revenue summaries. |
| Audit Logs | Authorized administrators | Review security and business events. |

## 4. Layout Principles

- Use a left navigation shell for desktop operations.
- Use compact page headers with actions aligned to the right.
- Use tables for operational lists with filters above the table.
- Use detail panels for create/edit forms.
- Avoid oversized marketing-style sections inside the application.
- Keep cards limited to dashboards and repeated summary items.

## 5. Dashboard Requirements

Dashboard should include:

- Today's trips.
- Active drivers.
- Available vehicles.
- Active routes.
- Estimated revenue.
- Queue or dispatch activity.
- Compliance alerts.
- Recent operational events.

## 6. Form Requirements

Forms should:

- Mark required fields clearly.
- Validate before submission.
- Show field-level errors near the field.
- Preserve user input when validation fails.
- Use consistent save, cancel, and delete patterns.
- Confirm destructive actions.

## 7. Table Requirements

Management tables should support:

- Search.
- Filter by status.
- Pagination.
- Sorting where useful.
- Row actions.
- Empty states.
- Loading states.
- Error states.

## 8. Visual Design Baseline

Use the TaxiSphere branding baseline:

- Azure blue for primary brand identity and key actions.
- Emerald green for healthy or successful operational state.
- Orange for warnings or dispatch urgency.
- Neutral grays for structure and readable text.
- Professional typography using Inter in the application.

## 9. Accessibility Baseline

- Text contrast must be readable.
- Keyboard navigation must work for core workflows.
- Form fields must have labels.
- Error messages must be understandable.
- Icons must not be the only way to understand critical actions.

## 10. Open Design Items

| Item | Decision Needed |
| --- | --- |
| UI component library | Confirm Angular Material or alternative during frontend setup. |
| Design mockups | Create dashboard, dispatch console, and management screen wireframes. |
| Mobile layout | Define responsive behavior for tablet and phone views. |
| Theme tokens | Convert brand guide into frontend design tokens. |
