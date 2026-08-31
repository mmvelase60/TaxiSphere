---
document_id: TS-ARC-001
title: TaxiSphere C4 Architecture Views
version: 0.1.0
status: Draft
classification: Internal Architecture
owner: Solution Architecture Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere C4 Architecture Views

## Executive Summary

This document captures the initial C4 architecture views for TaxiSphere: system context, container view, and backend component view. These diagrams support TS-SAD-001 and will evolve as implementation begins.

## 1. System Context

```mermaid
flowchart LR
    PlatformAdmin[Platform Administrator]
    AssocAdmin[Association Administrator]
    Dispatcher[Dispatcher]
    RankManager[Rank Manager]
    Owner[Taxi Owner]
    Driver[Driver]
    Finance[Finance Officer]
    Passenger[Passenger - Future]

    TaxiSphere[TaxiSphere Enterprise Mobility Platform]

    Email[Email Provider - Future]
    SMS[SMS Provider - Future]
    Maps[Maps Provider - Future]
    Payments[Payment Gateway - Future]

    PlatformAdmin --> TaxiSphere
    AssocAdmin --> TaxiSphere
    Dispatcher --> TaxiSphere
    RankManager --> TaxiSphere
    Owner --> TaxiSphere
    Driver --> TaxiSphere
    Finance --> TaxiSphere
    Passenger -. later release .-> TaxiSphere

    TaxiSphere -. notifications .-> Email
    TaxiSphere -. sms .-> SMS
    TaxiSphere -. geolocation .-> Maps
    TaxiSphere -. payments .-> Payments
```

## 2. Container View

```mermaid
flowchart TB
    Users[Users] --> Web[Angular Web Application]
    Web --> Api[Spring Boot API]
    Api --> Db[(MySQL Database)]
    Api --> Logs[Structured Logs]
    Api --> Metrics[Metrics Endpoint]
    Api -. future .-> Mail[Email/SMS Provider]
    Api -. future .-> Storage[Document Storage]
    Api -. future .-> Cache[Redis Cache]
```

## 3. Backend Component View

```mermaid
flowchart TB
    Controllers[REST Controllers] --> Security[Security Filters]
    Security --> TenantContext[Tenant Context Resolver]
    TenantContext --> AppServices[Application Services]

    AppServices --> Tenant[Tenant Module]
    AppServices --> Identity[Identity Module]
    AppServices --> Association[Association Module]
    AppServices --> Rank[Rank Operations Module]
    AppServices --> Driver[Driver Module]
    AppServices --> Vehicle[Vehicle Module]
    AppServices --> Route[Route Module]
    AppServices --> Trip[Dispatch and Trip Module]
    AppServices --> Reporting[Reporting Module]
    AppServices --> Audit[Audit Module]

    Tenant --> Repositories[Repositories]
    Identity --> Repositories
    Association --> Repositories
    Rank --> Repositories
    Driver --> Repositories
    Vehicle --> Repositories
    Route --> Repositories
    Trip --> Repositories
    Reporting --> Repositories
    Audit --> Repositories
    Repositories --> Database[(Relational Database)]
```

## 4. Diagram Rules

- C4 diagrams must stay aligned with TS-SAD-001.
- Container names must match deployable units.
- Component names must match backend module names where practical.
- Future integrations must be marked as future until implemented.
- Diagram changes that alter architecture intent should reference an ADR.
