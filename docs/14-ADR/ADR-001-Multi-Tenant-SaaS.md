---
document_id: ADR-001
title: Adopt a Multi-Tenant SaaS Architecture
version: 1.0.0
status: Accepted
date: 2026-07-31
decision_makers:
  - Solution Architecture Office
  - Product Owner
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - TS-STD-001
  - TS-ENG-001
---

# ADR-001 – Adopt a Multi-Tenant SaaS Architecture

> **Status:** Accepted

---

# Document Control

| Property | Value |
|----------|-------|
| ADR ID | ADR-001 |
| Title | Adopt a Multi-Tenant SaaS Architecture |
| Status | Accepted |
| Version | 1.0.0 |
| Product | TaxiSphere Enterprise Mobility Platform |
| Owner | Solution Architecture Office |

---

# 1. Context

TaxiSphere is being developed as a cloud-native enterprise platform that will initially serve taxi associations within South Africa and later expand across Africa.

The platform must support multiple independent organizations while maintaining:

- Data isolation
- Security
- Scalability
- High availability
- Cost efficiency
- Centralized management

Each taxi association should operate independently while sharing the same software platform.

---

# 2. Problem Statement

A traditional single-tenant architecture would require a separate deployment for every taxi association.

This approach introduces:

- Increased infrastructure costs
- Higher maintenance overhead
- Duplicate deployments
- Complex upgrades
- Inconsistent software versions
- Poor scalability

TaxiSphere requires an architecture capable of supporting hundreds or thousands of organizations from a single platform.

---

# 3. Decision

TaxiSphere shall adopt a **Multi-Tenant Software-as-a-Service (SaaS)** architecture.

Each tenant (for example, a taxi association or transport operator) will:

- Have isolated business data
- Maintain its own users and roles
- Configure business-specific settings
- Operate independently
- Share the same application deployment

---

# 4. Decision Drivers

The decision is based on the following priorities:

- Scalability
- Operational efficiency
- Lower hosting costs
- Centralized deployment
- Simplified maintenance
- Consistent feature delivery
- Security
- Future expansion across Africa

---

# 5. Considered Options

## Option 1 – Single Tenant

Each customer has:

- Separate application
- Separate database
- Separate deployment

### Advantages

- Strong isolation
- Simple mental model

### Disadvantages

- Expensive
- Difficult to maintain
- Poor scalability
- Slow upgrades

---

## Option 2 – Multi-Tenant SaaS (Selected)

Single platform

Multiple tenants

Shared infrastructure

Tenant isolation

### Advantages

- Lower operational cost
- Easier upgrades
- Faster feature rollout
- Better resource utilization
- Centralized monitoring
- Supports rapid growth

### Disadvantages

- Higher architectural complexity
- Strong tenant isolation required
- Increased security considerations

---

# 6. Decision Outcome

The Multi-Tenant SaaS architecture has been selected.

All future design decisions must support this architecture.

---

# 7. Tenant Model

Each tenant represents an independent organization.

Examples include:

- Taxi Association
- Bus Company
- Shuttle Company
- Logistics Operator
- Government Transport Agency (future)

Each tenant owns:

- Users
- Drivers
- Vehicles
- Routes
- Schedules
- Trips
- Payments
- Reports
- Configuration

---

# 8. Data Isolation Strategy

Every business record shall belong to exactly one tenant.

Examples:

```
Tenant A
 ├── Drivers
 ├── Vehicles
 ├── Trips
 └── Payments

Tenant B
 ├── Drivers
 ├── Vehicles
 ├── Trips
 └── Payments
```

Tenant A must never access Tenant B's information.

---

# 9. Authentication

Authentication shall support:

- Username/password
- JWT
- Refresh Tokens
- Role-Based Access Control (RBAC)

Future roadmap:

- OAuth2
- Azure Active Directory
- Google Sign-In
- Microsoft Entra ID

---

# 10. Authorization

Permissions shall be evaluated using:

- Tenant
- User
- Role
- Permission

Every request must verify:

1. Identity
2. Tenant
3. Role
4. Permission

---

# 11. Database Strategy

Initial implementation:

**Shared Database**

**Shared Schema**

Every business table contains:

```
tenant_id
```

Example:

```
driver

driver_id
tenant_id
name
license_number
status
```

Every query must filter by:

```
tenant_id
```

Future options:

- Shared database / multiple schemas
- Database per tenant

---

# 12. API Design

Every secured API request must include tenant context.

Example:

```
GET /api/v1/drivers
```

Tenant is resolved through the authenticated identity rather than being supplied by the client.

---

# 13. Security Principles

The platform shall enforce:

- Tenant isolation
- Encryption in transit
- Encryption at rest
- Audit logging
- Least privilege
- Secure defaults
- Input validation
- Output encoding

---

# 14. Operational Benefits

The selected architecture enables:

- One deployment
- One CI/CD pipeline
- One monitoring platform
- Centralized logging
- Easier upgrades
- Simplified disaster recovery

---

# 15. Risks

| Risk | Mitigation |
|------|------------|
| Tenant data leakage | Enforce tenant filtering at all layers |
| Authorization failures | Centralized security framework |
| Performance degradation | Database indexing, caching and monitoring |
| Complex onboarding | Automated tenant provisioning |

---

# 16. Consequences

Positive:

- Scalable architecture
- Lower infrastructure costs
- Faster feature delivery
- Easier operations
- Better customer onboarding

Negative:

- More complex application design
- Strong governance required
- Security must be continuously validated

---

# 17. Future Evolution

TaxiSphere may evolve to support:

- Database per tenant
- Regional deployments
- Cross-country hosting
- Event-driven architecture
- Microservices (when justified)
- AI-powered operational analytics

---

# 18. Compliance

This decision complies with:

- TS-STD-001 – Documentation & Engineering Standards
- TS-ENG-001 – Engineering Handbook

---

# Decision Summary

**Decision:** Adopt a Multi-Tenant SaaS Architecture

**Status:** Accepted

**Rationale:** A multi-tenant SaaS architecture provides the scalability, operational efficiency, centralized management, and cost effectiveness required to support TaxiSphere's vision of serving multiple transport organizations across South Africa and, ultimately, Africa from a single cloud-native platform.