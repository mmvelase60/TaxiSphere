---
Document ID: TS-BCK-024
Version: 1.0.0
Status: Draft
Owner: Backend Engineering
Classification: Internal
Last Updated: 2026-09-01
---

# TS-BCK-024 — Passenger Foundation

## Purpose

This document defines the first passenger profile foundation for TaxiSphere.

## Business Capability

Passenger management gives TaxiSphere a tenant-owned customer profile base for future ticketing, payments, trip notifications, loyalty, and mobile application features.

## Domain Model

| Entity | Purpose |
| --- | --- |
| `Passenger` | Stores tenant-owned passenger contact and profile data |
| `PassengerStatus` | Tracks passenger lifecycle: `ACTIVE`, `SUSPENDED`, or `INACTIVE` |

## Endpoints

| Method | Path | Roles | Purpose |
| --- | --- | --- | --- |
| GET | `/api/v1/passengers` | Operations and finance roles | List tenant passenger profiles |
| GET | `/api/v1/passengers/{passengerId}` | Operations and finance roles | Get one tenant passenger profile |
| POST | `/api/v1/passengers` | `ASSOCIATION_ADMIN`, `OPERATIONS_MANAGER`, `RANK_MANAGER`, `DISPATCHER` | Create a passenger profile |

## Tenant Isolation

Passenger records are created under the authenticated tenant and associated with that tenant's taxi association. Users cannot read or create passenger records for another tenant.

## Design Notes

- Passenger profiles are separate from user accounts in Version 1.
- `userAccountId` is optional to support future passenger portal and mobile app login.
- Phone number is required and unique per tenant.
- Email is optional and unique per tenant when supplied.

## Future Enhancements

- Passenger portal registration
- Passenger mobile application
- QR ticketing
- Digital wallet
- Passenger trip history
- Passenger notification preferences
- Passenger loyalty and rewards