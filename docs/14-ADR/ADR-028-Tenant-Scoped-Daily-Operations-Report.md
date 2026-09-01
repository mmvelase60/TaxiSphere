# ADR-028 — Create Tenant-Scoped Daily Operations Report

## Status

Accepted

## Context

TaxiSphere now supports trips, trip lifecycle actions, and dashboard metrics. Association users need a formal reporting endpoint that summarizes operational performance for a selected business date.

## Decision

Add a tenant-scoped daily operations report endpoint that summarizes trips by lifecycle status, total passengers, and total revenue for a requested date.

## Consequences

- Operations and finance users can review daily activity without manually exporting trip lists.
- The reporting module starts with tenant-safe aggregate queries.
- Future reporting features can extend this foundation into weekly, monthly, route, driver, vehicle, PDF, and Excel reports.