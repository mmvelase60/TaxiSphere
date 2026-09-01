---
Document ID: TS-BCK-020
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-020 — Finance Foundation

## Purpose

This document defines the first finance module foundation for TaxiSphere.

## Business Capability

Finance enables taxi associations to record income, expenses, daily collections, and future settlement activity in a tenant-isolated ledger.

## Domain Model

| Entity | Purpose |
| --- | --- |
| `FinancialTransaction` | Records tenant-owned money movement |
| `FinanceTransactionType` | Classifies a transaction as `INCOME` or `EXPENSE` |
| `FinanceTransactionCategory` | Groups money movement by operational category |

## Endpoints

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/finance/transactions` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `FINANCE_OFFICER` | List tenant finance transactions |
| GET | `/api/v1/finance/transactions/{transactionId}` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `FINANCE_OFFICER` | Get one tenant finance transaction |
| POST | `/api/v1/finance/transactions` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `FINANCE_OFFICER` | Record a finance transaction |
| GET | `/api/v1/finance/summary/daily` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `FINANCE_OFFICER` | Return daily income, expenses, and net amount |

## Transaction Categories

| Category | Meaning |
| --- | --- |
| `TRIP_REVENUE` | Income linked to trip revenue |
| `DAILY_COLLECTION` | Cash or operational collection for a business date |
| `FUEL` | Fuel expense |
| `MAINTENANCE` | Vehicle maintenance expense |
| `LICENSING` | License-related expense |
| `INSURANCE` | Insurance-related expense |
| `OWNER_SETTLEMENT` | Settlement to taxi owner |
| `DRIVER_PAYOUT` | Driver payout or allowance |
| `OTHER` | Uncategorized finance transaction |

## Tenant Isolation

All finance transactions are written with the current tenant ID and associated with the tenant association profile. Users cannot create or read finance records outside their tenant context.

## Future Enhancements

- Trip-to-finance posting automation
- Owner settlements
- Driver payouts
- Expense approvals
- PDF and Excel finance exports
- Monthly statements
- Payment gateway integration