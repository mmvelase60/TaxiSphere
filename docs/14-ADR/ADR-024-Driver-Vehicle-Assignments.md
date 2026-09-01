# ADR-024 — Model Driver Vehicle Assignments Separately

## Status

Accepted

## Context

Drivers and vehicles are independent tenant-owned resources. Dispatch and trip modules need a reliable way to know which driver is operating which vehicle at a point in time.

## Decision

Create a separate tenant-owned `vehicle_assignment` table linking drivers and vehicles with assignment dates and lifecycle status.

## Consequences

- Driver and vehicle history can be tracked over time.
- Active assignment rules can be enforced before dispatch.
- Future releases can support reassignment, assignment history, and audit reporting.
