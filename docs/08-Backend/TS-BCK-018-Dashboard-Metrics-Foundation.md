---
Document ID: TS-BCK-018
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-018 — Dashboard Metrics Foundation

## Purpose

This document defines the first operational dashboard metrics endpoint for TaxiSphere.

## Business Capability

Dashboard metrics give association and operations users a quick view of current tenant activity, operational assets, and daily revenue.

## Endpoint

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/dashboard/overview` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER`, `FINANCE_OFFICER` | Return current tenant overview metrics |

## Metrics

| Metric | Meaning |
| --- | --- |
| `todayTrips` | Trips dispatched today |
| `activeTrips` | Trips currently dispatched or departed |
| `todayRevenue` | Revenue from trips dispatched today |
| `totalDrivers` | Driver records in the tenant |
| `availableDrivers` | Drivers marked available |
| `totalVehicles` | Vehicle records in the tenant |
| `availableVehicles` | Vehicles marked available |
| `totalRanks` | Taxi ranks in the tenant |
| `totalRoutes` | Taxi routes in the tenant |

## Tenant Isolation

All metrics are calculated using the tenant ID resolved from the authenticated JWT.
