# ADR-029 — Create Tenant-Scoped Finance Ledger

## Status

Accepted

## Context

TaxiSphere now supports trip dispatching, reporting, and operational dashboard metrics. The platform needs a finance foundation to record income and expenses in a structured way before advanced accounting, settlements, and exports are introduced.

## Decision

Create a tenant-scoped finance ledger using a `FinancialTransaction` entity. Each transaction records tenant ID, association ID, type, category, amount, business date, optional description, and optional reference details.

## Consequences

- Finance users can record daily collections and operational expenses.
- Daily finance summaries can be calculated from the ledger.
- Future settlement, payout, invoicing, and accounting workflows have a stable foundation.
- Every finance record remains tenant-isolated by design.