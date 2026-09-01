---
Document ID: TS-BCK-019
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-019 — Reporting Foundation

## Purpose

This document defines the first reporting endpoint for TaxiSphere tenant operations.

## Business Capability

Reporting converts operational trip records into management information for association administrators, operations managers, rank managers, dispatchers, and finance officers.

## Endpoint

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/reports/daily-operations` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER`, `FINANCE_OFFICER` | Return daily tenant operations totals |

## Query Parameters

| Parameter | Required | Format | Purpose |
| --- | --- | --- | --- |
| `date` | No | `YYYY-MM-DD` | Business date to report; defaults to current UTC date |

## Metrics

| Metric | Meaning |
| --- | --- |
| `dispatchedTrips` | Trips dispatched but not yet departed |
| `departedTrips` | Trips currently on route |
| `arrivedTrips` | Trips completed for the business date |
| `cancelledTrips` | Trips cancelled for the business date |
| `totalTrips` | Sum of all trip statuses for the business date |
| `totalPassengers` | Sum of passengers recorded on trips for the business date |
| `totalRevenue` | Sum of trip revenue for the business date |

## Tenant Isolation

All report calculations use the tenant ID resolved from the authenticated request context. Reports never aggregate across tenants unless a future platform-level reporting module explicitly allows it.

## Future Enhancements

- Weekly and monthly reports
- Route performance reports
- Driver performance reports
- Vehicle utilization reports
- Export to PDF and Excel
- Scheduled report delivery