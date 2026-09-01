---
Document ID: TS-BCK-022
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-022 — Compliance Foundation

## Purpose

This document defines the first compliance foundation for TaxiSphere.

## Business Capability

Compliance helps taxi associations monitor operational readiness by identifying expired and soon-to-expire driver and vehicle documents.

## Endpoint

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/compliance/overview` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER` | Return tenant compliance expiry counts |

## Query Parameters

| Parameter | Required | Default | Purpose |
| --- | --- | --- | --- |
| `warningDays` | No | `30` | Number of days ahead used to classify expiring items |

## Metrics

| Metric | Meaning |
| --- | --- |
| `expiredDriverLicenses` | Driver licences with expiry dates before the business date |
| `expiringDriverLicenses` | Driver licences expiring within the warning window |
| `expiredDriverPdps` | PDP documents with expiry dates before the business date |
| `expiringDriverPdps` | PDP documents expiring within the warning window |
| `expiredRoadworthyCertificates` | Vehicle roadworthy certificates expired before the business date |
| `expiringRoadworthyCertificates` | Vehicle roadworthy certificates expiring within the warning window |
| `expiredInsurancePolicies` | Vehicle insurance policies expired before the business date |
| `expiringInsurancePolicies` | Vehicle insurance policies expiring within the warning window |
| `totalExpiredItems` | Total expired compliance items |
| `totalExpiringItems` | Total expiring compliance items |

## Tenant Isolation

All compliance metrics are calculated from driver and vehicle records belonging to the authenticated tenant.

## Design Notes

- Version 1 derives compliance intelligence from existing driver and vehicle expiry fields.
- No separate compliance case table is introduced yet.
- Future workflows can add inspections, approvals, reminders, and enforcement actions.

## Future Enhancements

- Compliance cases
- Inspection records
- Document upload and verification
- Automated reminder notifications
- Compliance dashboard charts
- Driver and vehicle suspension workflows