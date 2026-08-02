---
document_id: ADR-003
title: Adopt the Technology Stack Baseline
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
---

# ADR-003 – Adopt the Technology Stack Baseline

> **Status:** Accepted

---

# Document Control

| Property | Value |
|----------|-------|
| ADR ID | ADR-003 |
| Title | Adopt the Technology Stack Baseline |
| Status | Accepted |
| Version | 1.0.0 |
| Product | TaxiSphere Enterprise Mobility Platform |
| Owner | Solution Architecture Office |

---

# 1. Context

TaxiSphere requires a standardized technology stack to ensure consistency, maintainability, developer productivity, and long-term platform stability.

Selecting a common technology baseline reduces unnecessary complexity and enables the engineering team to build, test, deploy, and operate the platform efficiently.

---

# 2. Problem Statement

Without an approved technology baseline:

- Teams may select inconsistent technologies.
- Skills become fragmented.
- Operational complexity increases.
- Documentation becomes inconsistent.
- Maintenance costs increase.
- Integration becomes more difficult.

TaxiSphere requires an approved and governed technology stack that all contributors will follow.

---

# 3. Decision

TaxiSphere shall adopt the following technology stack as the official baseline for Version 1 of the platform.

Technology selections will prioritize:

- Long-Term Support (LTS)
- Stability
- Enterprise adoption
- Strong community support
- Security
- Maintainability

---

# 4. Decision Drivers

The technology stack shall support:

- Cloud-native deployment
- Multi-Tenant SaaS architecture
- Modular Monolith architecture
- Scalability
- Security
- Automation
- DevOps
- Long-term maintainability

---

# 5. Approved Technology Stack

## Backend

| Technology | Purpose |
|------------|---------|
| Java (latest stable release supported by Spring Boot) | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | Data Access |
| Hibernate | ORM |
| Maven | Build Management |

---

## Frontend

| Technology | Purpose |
|------------|---------|
| Angular (latest stable production release) | Frontend Framework |
| TypeScript | Programming Language |
| HTML5 | Markup |
| CSS3 | Styling |
| RxJS | Reactive Programming |

---

## Database

| Technology | Purpose |
|------------|---------|
| MySQL | Relational Database |

---

## API

| Technology | Purpose |
|------------|---------|
| REST | Service Communication |
| OpenAPI 3.x | API Documentation |
| JSON | Data Exchange |

---

## Authentication & Security

| Technology | Purpose |
|------------|---------|
| JWT | Authentication |
| RBAC | Authorization |
| BCrypt | Password Hashing |
| HTTPS | Secure Communication |

---

## DevOps

| Technology | Purpose |
|------------|---------|
| Git | Version Control |
| GitHub | Source Repository |
| GitHub Actions | Continuous Integration |
| Docker | Containerization |
| Kubernetes | Container Orchestration |
| Terraform | Infrastructure as Code |

---

## Cloud

| Technology | Purpose |
|------------|---------|
| Microsoft Azure | Cloud Platform |

---

## Development Tools

| Technology | Purpose |
|------------|---------|
| IntelliJ IDEA | Backend Development |
| Visual Studio Code | Frontend & Documentation |
| Postman | API Testing |
| MySQL Workbench | Database Administration |
| GitHub Desktop / Git CLI | Source Control |

---

# 6. Version Management

Technology versions shall:

- Be documented.
- Be reviewed before upgrades.
- Prefer LTS or stable production releases.
- Avoid experimental releases in production.

Major upgrades require a separate ADR.

---

# 7. Technology Selection Principles

Technology adoption shall consider:

- Enterprise maturity
- Security
- Community support
- Vendor support
- Documentation quality
- Performance
- Maintainability
- Learning curve
- Licensing

---

# 8. Excluded Technologies

The following technologies are not part of the Version 1 baseline but may be evaluated later:

- Apache Kafka
- RabbitMQ
- Redis
- PostgreSQL
- MongoDB
- Elasticsearch
- GraphQL
- gRPC
- Native Mobile Frameworks

Future adoption requires a dedicated ADR.

---

# 9.
```