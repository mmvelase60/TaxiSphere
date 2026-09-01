# ADR-025 — Capture Trips as Dispatch Events

## Status

Accepted

## Context

TaxiSphere needs trip records to connect daily dispatch operations, driver activity, vehicle usage, passenger count, route performance, and revenue reporting.

## Decision

Create a tenant-owned `trip` table that captures the active vehicle assignment, driver, vehicle, route, passenger count, fare per passenger, total revenue, and dispatch lifecycle timestamps.

## Consequences

- Trips become the foundation for dashboard and finance reports.
- Fare and assignment details are preserved at dispatch time.
- Future lifecycle actions can extend trips from `DISPATCHED` to `DEPARTED`, `ARRIVED`, or `CANCELLED`.
