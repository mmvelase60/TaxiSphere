---
Document ID: TS-BCK-016
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-016 — Trip Management Foundation

## Purpose

This document defines the first trip dispatch capability for TaxiSphere.

## Business Capability

Trip management records operational dispatch events by linking an active driver-vehicle assignment to a route, passenger count, fare, and revenue calculation.

## Endpoints

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/trips` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER`, `FINANCE_OFFICER` | List current tenant trips |
| GET | `/api/v1/trips/{tripId}` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER`, `FINANCE_OFFICER` | View one tenant trip |
| POST | `/api/v1/trips/dispatch` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER` | Dispatch a trip |

## Business Rules

- Trip operations require tenant context from a valid JWT.
- A trip must reference an active vehicle assignment.
- A trip must reference a route belonging to the current tenant.
- Driver and vehicle are copied from the active assignment at dispatch time.
- Fare per passenger is copied from the route at dispatch time.
- Total revenue is calculated as `farePerPassenger * passengerCount`.
- New trips start in `DISPATCHED` status.
- Trip lookups are always scoped by `tenant_id`.

## Future Enhancements

- Depart trip
- Arrive trip
- Cancel trip
- Fuel estimation
- Route profitability
- Passenger ticketing
