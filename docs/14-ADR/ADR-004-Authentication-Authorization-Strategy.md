---
document_id: ADR-004
title: Adopt the Authentication & Authorization Strategy
version: 1.0.0
status: Accepted
date: 2026-08-01
decision_makers:
  - Solution Architecture Office
  - Product Owner
product: TaxiSphere Enterprise Mobility Platform
related_documents:
  - TS-STD-001
  - TS-ENG-001
  - ADR-001
  - ADR-002
  - ADR-003
---

# ADR-004 – Authentication & Authorization Strategy

> **Status:** Accepted

---

# Document Control

| Property | Value |
|----------|-------|
| ADR ID | ADR-004 |
| Title | Authentication & Authorization Strategy |
| Status | Accepted |
| Version | 1.0.0 |
| Product | TaxiSphere Enterprise Mobility Platform |
| Owner | Solution Architecture Office |

---

# 1. Context

TaxiSphere is a cloud-native, multi-tenant SaaS platform serving multiple independent transport organizations.

The platform requires a secure, scalable, and centralized identity solution capable of:

- Authenticating users
- Enforcing tenant isolation
- Managing permissions
- Supporting future enterprise identity providers

---

# 2. Problem Statement

Without a standardized authentication and authorization strategy:

- Users may gain unauthorized access.
- Tenant boundaries could be compromised.
- Security implementations become inconsistent.
- Auditing becomes difficult.
- APIs become vulnerable.

A single enterprise security model is required.

---

# 3. Decision

TaxiSphere Version 1 shall adopt:

- JWT Authentication
- Refresh Tokens
- Role-Based Access Control (RBAC)
- Tenant-Aware Authorization
- BCrypt Password Hashing
- Spring Security

Future identity providers will integrate through OAuth2/OpenID Connect.

---

# 4. Decision Drivers

The selected approach must provide:

- Strong security
- High performance
- Stateless APIs
- Multi-tenant support
- Enterprise scalability
- Easy integration
- Centralized authorization

---

# 5. Authentication Strategy

Authentication verifies the identity of a user.

The login flow:

```
User

↓

Login Request

↓

Spring Security

↓

Credential Validation

↓

JWT Token Issued

↓

API Requests

↓

JWT Validation

↓

Authenticated User
```

Every authenticated request must include a valid JWT access token.

---

# 6. Authorization Strategy

Authorization determines what an authenticated user is allowed to do.

Authorization evaluates:

- Tenant
- User
- Role
- Permission

Every secured request must satisfy all four checks before access is granted.

---

# 7. Tenant-Aware Security

Every authenticated user belongs to exactly one tenant.

Example:

```
Tenant A

Admin
Dispatcher
Driver

------------------------

Tenant B

Admin
Dispatcher
Driver
```

Users may never access another tenant's resources.

---

# 8. Role-Based Access Control (RBAC)

Initial platform roles:

- Platform Administrator
- Tenant Administrator
- Dispatcher
- Driver
- Fleet Manager
- Passenger
- Auditor
- Support Engineer

Each role is assigned permissions based on business responsibilities.

---

# 9. Permission Model

Permissions follow the format:

```
resource:action
```

Examples:

```
driver:create

driver:update

driver:view

driver:delete

vehicle:create

trip:assign

payment:view

report:generate
```

Roles are collections of permissions.

---

# 10. JWT Structure

The access token should contain claims such as:

```
Subject

Tenant ID

User ID

Roles

Permissions

Issued Time

Expiration Time
```

Sensitive business information shall never be stored inside the token.

---

# 11. Refresh Tokens

Access Tokens:

- Short lifetime
- Used for API access

Refresh Tokens:

- Longer lifetime
- Used to obtain new access tokens
- Can be revoked

---

# 12. Password Policy

Passwords must:

- Meet minimum length requirements
- Contain a combination of character types
- Never be stored in plain text
- Be hashed using BCrypt

Password reset links shall expire after a configurable period.

---

# 13. API Security

Every protected endpoint shall require:

```
Authorization:

Bearer <JWT>
```

Public endpoints shall be explicitly defined.

---

# 14. Audit Logging

The platform shall log:

- Login success
- Login failure
- Logout
- Password changes
- Permission changes
- Role changes
- Failed authorization attempts

Audit records shall include:

- Timestamp
- User ID
- Tenant ID
- IP Address
- Action

---

# 15. Security Principles

TaxiSphere follows:

- Least Privilege
- Secure by Default
- Defense in Depth
- Zero Trust
- Tenant Isolation
- Encryption in Transit
- Encryption at Rest

---

# 16. Future Evolution

Future versions may support:

- OAuth2
- OpenID Connect
- Microsoft Entra ID
- Google Identity
- Multi-Factor Authentication (MFA)
- Passwordless Authentication
- Biometric Authentication
- Single Sign-On (SSO)

Each enhancement requires a dedicated ADR before implementation.

---

# 17. Risks

| Risk | Mitigation |
|------|------------|
| Token theft | Short-lived access tokens, HTTPS, secure storage |
| Weak passwords | Strong password policy and BCrypt hashing |
| Privilege escalation | RBAC, permission validation, security testing |
| Cross-tenant access | Tenant-aware authorization in every request |
| Token replay | Refresh token rotation and revocation |

---

# 18. Consequences

## Positive

- Centralized authentication
- Consistent authorization
- Secure API access
- Strong tenant isolation
- Scalable identity management
- Enterprise-ready security

## Negative

- Additional implementation complexity
- Token lifecycle management required
- Ongoing security monitoring required

---

# 19. Compliance

This decision complies with:

- TS-STD-001 – Documentation & Engineering Standards
- TS-ENG-001 – Engineering Handbook
- ADR-001 – Multi-Tenant SaaS Architecture
- ADR-002 – Modular Monolith Architecture
- ADR-003 – Technology Stack Baseline

---

# Decision Summary

**Decision:** Adopt JWT-based Authentication, Role-Based Access Control (RBAC), Tenant-Aware Authorization, and Spring Security as the standard security architecture for TaxiSphere Version 1.

**Status:** Accepted.

**Rationale:** This approach provides a secure, scalable, stateless, and enterprise-ready authentication and authorization framework that aligns with TaxiSphere's cloud-native, multi-tenant SaaS architecture while allowing future integration with enterprise identity providers.