# ADR-026 — Enforce Trip Lifecycle Transitions in the Domain

## Status

Accepted

## Context

Trip state changes are business rules, not controller concerns. TaxiSphere needs consistent lifecycle behavior across APIs, tests, and future asynchronous workflows.

## Decision

Implement trip lifecycle transition methods on the `Trip` domain entity and expose service methods for depart, arrive, and cancel actions.

## Consequences

- Invalid trip transitions are rejected consistently.
- Future APIs, jobs, or event handlers can reuse the same domain rules.
- Trip timestamps are captured when lifecycle transitions occur.
