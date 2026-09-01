---
Document ID: TS-BCK-023
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-023 — Maintenance Foundation

## Purpose

This document defines the first vehicle maintenance foundation for TaxiSphere.

## Business Capability

Maintenance allows taxi associations to schedule, track, complete, and report on vehicle service and repair activity. This supports fleet reliability, compliance readiness, and cost visibility.

## Domain Model

| Entity | Purpose |
| --- | --- |
| `MaintenanceRecord` | Tenant-owned maintenance activity linked to a vehicle |
| `MaintenanceType` | Groups work by service, repair, inspection, tyres, brakes, engine, bodywork, or other |
| `MaintenanceStatus` | Tracks lifecycle: `SCHEDULED`, `IN_PROGRESS`, `COMPLETED`, or `CANCELLED` |

## Endpoints

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/maintenance` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER` | List tenant maintenance records |
| GET | `/api/v1/maintenance/{recordId}` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER` | Get one tenant maintenance record |
| GET | `/api/v1/maintenance/vehicles/{vehicleId}` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER` | List maintenance records for a tenant vehicle |
| POST | `/api/v1/maintenance` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER` | Schedule maintenance |
| POST | `/api/v1/maintenance/{recordId}/start` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER` | Mark maintenance in progress |
| POST | `/api/v1/maintenance/{recordId}/complete` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER` | Complete maintenance with final cost |
| POST | `/api/v1/maintenance/{recordId}/cancel` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER` | Cancel maintenance |
| GET | `/api/v1/maintenance/summary` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER` | Return maintenance status and date-range cost summary |

## Tenant Isolation

Maintenance records are linked to vehicles that belong to the authenticated tenant. A user cannot schedule or view maintenance for vehicles outside their tenant.

## Design Notes

- Version 1 tracks maintenance records directly against vehicles.
- Costs are stored on the maintenance record for operational visibility.
- Future finance integration can post completed maintenance costs into the finance ledger.

## Future Enhancements

- Maintenance approval workflow
- Maintenance vendor management
- Vehicle downtime tracking
- Automatic finance expense posting
- Maintenance reminders
- Predictive maintenance analytics