---
document_id: TS-STD-001
title: TaxiSphere Documentation and Branding Standard
version: 1.0.0
status: Draft
classification: Internal Engineering Standard
owner: Solution Architecture Office
project: TaxiSphere Enterprise Mobility Platform
prepared_by: Sphere Technologies (Pty) Ltd
last_updated: 2026-07-20
---

# TaxiSphere Documentation and Branding Standard

## Document Control

| Field | Value |
| --- | --- |
| Document ID | TS-STD-001 |
| Document Title | TaxiSphere Documentation and Branding Standard |
| Version | 1.0.0 |
| Status | Draft |
| Classification | Internal Engineering Standard |
| Owner | Solution Architecture Office |
| Project | TaxiSphere Enterprise Mobility Platform |
| Prepared By | Sphere Technologies (Pty) Ltd |
| Last Updated | 2026-07-20 |

## Revision History

| Version | Date | Author | Description |
| --- | --- | --- | --- |
| 1.0.0 | 2026-07-20 | Sphere Technologies | Initial draft of the documentation and branding standard. |

## Approval

| Role | Name | Status | Date |
| --- | --- | --- | --- |
| Product Owner | To be confirmed | Pending | To be confirmed |
| Solution Architect | To be confirmed | Pending | To be confirmed |
| Engineering Lead | To be confirmed | Pending | To be confirmed |

## Table of Contents

1. Executive Summary
2. Purpose
3. Scope
4. Product Identity
5. Corporate Identity
6. Brand Standards
7. Documentation Standards
8. Document Numbering
9. Versioning Standard
10. Status Workflow
11. Repository Standards
12. Folder Standards
13. Naming Conventions
14. Markdown Standards
15. Diagram Standards
16. Traceability Standards
17. Quality Standards
18. Approval Workflow
19. Document Lifecycle
20. References
21. Glossary

## 1. Executive Summary

TaxiSphere is a cloud-native, multi-tenant SaaS platform designed to modernize taxi association operations through secure, scalable, and well-governed software.

This document defines the official documentation and branding standard for TaxiSphere. It establishes the rules for document structure, naming, versioning, repository organization, diagram conventions, traceability, and quality expectations.

The goal is to make every TaxiSphere artifact consistent, professional, maintainable, and suitable for use in an enterprise engineering environment.

## 2. Purpose

The purpose of this standard is to ensure that all TaxiSphere documents and engineering artifacts follow a common structure and visual identity.

This standard supports:

- Consistent documentation across the platform.
- Clear ownership and document control.
- Professional presentation for stakeholders.
- Traceability from business goals to implementation.
- Easier onboarding for future contributors.
- Stronger governance over engineering decisions.

## 3. Scope

This standard applies to all TaxiSphere project artifacts, including:

- Governance documents.
- Business documents.
- Requirements documents.
- Architecture documents.
- Domain model documents.
- Database design documents.
- API specifications.
- Frontend and backend design documents.
- Security documents.
- Testing documents.
- Operations and deployment documents.
- Architecture Decision Records.
- GitHub repository files.
- Diagram assets.
- Templates.

## 4. Product Identity

| Property | Value |
| --- | --- |
| Product Name | TaxiSphere |
| Full Product Name | TaxiSphere Enterprise Mobility Platform |
| Product Code | TS |
| Product Type | Enterprise Cloud Platform |
| Product Category | Mobility Technology |
| Deployment Model | Multi-Tenant SaaS |
| Initial Region | South Africa |
| Expansion Strategy | Africa |
| Target Customers | Taxi associations and transport operators |

## 5. Corporate Identity

| Property | Value |
| --- | --- |
| Company Name | Sphere Technologies (Pty) Ltd |
| Business Domain | Enterprise Software |
| Product Division | Mobility Solutions |
| Engineering Organization | TaxiSphere Engineering |
| Primary Platform | TaxiSphere Enterprise Mobility Platform |

## 6. Brand Standards

### 6.1 Brand Personality

TaxiSphere should feel:

- Professional.
- Reliable.
- Secure.
- Modern.
- Operationally focused.
- Easy to understand.

The brand should avoid overly playful language in formal documents. Engineering documents must be clear, concise, and defensible.

### 6.2 Color Palette

| Color | Usage | Meaning |
| --- | --- | --- |
| Azure Blue | Primary brand color | Trust, technology, cloud readiness |
| Emerald Green | Secondary color | Growth, mobility, success |
| Orange | Accent color | Movement, urgency, transport operations |
| Dark Gray | Text and UI foundations | Professional structure |
| Light Gray | Backgrounds and separators | Calm visual hierarchy |
| White | Document and interface base | Simplicity and clarity |

Exact hex values will be finalized in the TaxiSphere Brand Guide.

### 6.3 Typography

| Usage | Font |
| --- | --- |
| Formal documents | Aptos or Calibri |
| Web application | Inter |
| Source code | JetBrains Mono or Consolas |
| Diagrams | Inter or Aptos |

### 6.4 Logo Usage

The official TaxiSphere logo will be stored under:

```text
assets/branding/logos/
```

Future versions of this standard will define:

- Primary logo.
- Dark background logo.
- Light background logo.
- Product icon.
- Favicon.
- Minimum clear space.
- Incorrect logo usage.

## 7. Documentation Standards

Every controlled TaxiSphere document must include:

- Cover page or metadata block.
- Document control table.
- Revision history.
- Approval section.
- Table of contents.
- Executive summary.
- Purpose.
- Scope.
- Main content.
- References.
- Glossary, where relevant.
- Appendices, where relevant.

Documents should be written in clear professional language. Requirements and decisions must be specific enough for engineering, testing, and review.

## 8. Document Numbering

### 8.1 Document ID Format

TaxiSphere documents use the following format:

```text
TS-XXX-###
```

Where:

| Segment | Meaning |
| --- | --- |
| TS | TaxiSphere |
| XXX | Document category |
| ### | Sequential number |

### 8.2 Official Document Categories

| Document | ID |
| --- | --- |
| Documentation and Branding Standard | TS-STD-001 |
| Engineering Handbook | TS-ENG-001 |
| Product Vision and Strategy | TS-VSN-001 |
| Project Charter | TS-CHR-001 |
| Business Requirements Document | TS-BRD-001 |
| Software Requirements Specification | TS-SRS-001 |
| Software Architecture Document | TS-SAD-001 |
| Domain Model Specification | TS-DMS-001 |
| Database Design Specification | TS-DDS-001 |
| API Design Specification | TS-ADS-001 |
| UI/UX Specification | TS-UXS-001 |
| Security Architecture | TS-SEC-001 |
| Test Strategy | TS-TST-001 |
| Operations and Deployment Guide | TS-OPS-001 |

## 9. Versioning Standard

TaxiSphere documents use semantic versioning:

```text
Major.Minor.Patch
```

| Version Change | Meaning | Example |
| --- | --- | --- |
| Major | Significant restructure or scope change | 2.0.0 |
| Minor | New section or material content added | 1.1.0 |
| Patch | Typo, formatting, or minor correction | 1.0.1 |

## 10. Status Workflow

Controlled documents must use one of the following statuses:

| Status | Meaning |
| --- | --- |
| Draft | Work in progress and not yet approved |
| Under Review | Ready for stakeholder review |
| Approved | Accepted as the official baseline |
| Published | Released for wider use |
| Archived | Retired or replaced by a newer document |

## 11. Repository Standards

The TaxiSphere repository should use this top-level structure:

```text
TaxiSphere/
  .github/
  assets/
  backend/
  decisions/
  docker/
  docs/
  frontend/
  infrastructure/
  kubernetes/
  research/
  samples/
  scripts/
  terraform/
  tools/
  README.md
```

This structure separates documentation, source code, infrastructure, project assets, and research material.

## 12. Folder Standards

The `docs/` folder should use the following structure:

```text
docs/
  00-Governance/
  01-Business/
  02-Requirements/
  03-Architecture/
  04-Domain/
  05-Database/
  06-API/
  07-Frontend/
  08-Backend/
  09-Security/
  10-Testing/
  11-Operations/
  12-Deployment/
  13-Engineering-Handbook/
  14-ADR/
  15-Research/
  16-Templates/
  17-Wiki/
  18-Release-Notes/
  19-Compliance/
  20-Training/
```

Governance standards should be stored under:

```text
docs/00-Governance/Standards/
```

## 13. Naming Conventions

### 13.1 Documents

Document filenames should follow this pattern:

```text
DocumentID-Readable-Title.md
```

Example:

```text
TS-STD-001-Documentation-and-Branding-Standard.md
```

### 13.2 Requirements

| Requirement Type | Format |
| --- | --- |
| Business Requirement | BR-001 |
| Functional Requirement | FR-001 |
| Non-Functional Requirement | NFR-001 |
| Use Case | UC-001 |
| Business Rule | RULE-001 |
| Test Case | TC-001 |

Module-specific requirements may include a module prefix:

```text
FR-AUTH-001
FR-TENANT-001
FR-DRIVER-001
```

### 13.3 Architecture Decision Records

ADR filenames should follow this pattern:

```text
ADR-###-Short-Decision-Title.md
```

Example:

```text
ADR-001-Multi-Tenant-SaaS-Architecture.md
```

### 13.4 Branches

Git branches should follow these patterns:

```text
main
develop
feature/short-description
bugfix/short-description
hotfix/short-description
release/v1.0.0
docs/short-description
```

## 14. Markdown Standards

Markdown is the source of truth for TaxiSphere documentation.

Word and PDF files are treated as published artifacts generated from Markdown where practical.

Markdown documents should:

- Use front matter for metadata.
- Use consistent heading levels.
- Use tables for structured information.
- Use relative links between project documents.
- Use Mermaid diagrams for lightweight diagrams.
- Keep one topic per section.
- Avoid duplicated information where cross-references are better.

## 15. Diagram Standards

| Diagram Type | Standard |
| --- | --- |
| System Context | C4 Model |
| Container | C4 Model |
| Component | C4 Model |
| Deployment | UML Deployment |
| Sequence | UML Sequence |
| Activity | UML Activity |
| Use Case | UML Use Case |
| Business Process | BPMN 2.0 |
| Entity Relationship | Crow's Foot ERD |

Approved diagram tools:

| Tool | Usage |
| --- | --- |
| Mermaid | Lightweight Markdown diagrams |
| PlantUML | Version-controlled UML diagrams |
| diagrams.net | Architecture, ERD, and process diagrams |
| Figma | UI/UX design |

## 16. Traceability Standards

TaxiSphere uses end-to-end traceability to connect business intent to implementation.

Every major feature should trace through this lifecycle:

```text
Business Goal
  -> Business Requirement
  -> Functional Requirement
  -> Use Case
  -> Business Rule
  -> Architecture Decision
  -> API Contract
  -> Database Design
  -> Frontend Screen
  -> Backend Module
  -> Test Case
  -> Deployment and Monitoring
```

Implementation should not begin for major features until the business requirement, functional requirement, and acceptance criteria are defined.

## 17. Quality Standards

Every TaxiSphere artifact should be:

- Correct.
- Complete.
- Consistent.
- Verifiable.
- Traceable.
- Maintainable.
- Secure.
- Easy to review.
- Suitable for future contributors.

## 18. Approval Workflow

Controlled documents follow this approval workflow:

```text
Draft
  -> Technical Review
  -> Business Review
  -> Architecture Review
  -> Approved
  -> Published
```

For lightweight internal documents, Technical Review and Architecture Review may be combined.

## 19. Document Lifecycle

Every controlled document follows this lifecycle:

1. Create the draft.
2. Review the content.
3. Update based on feedback.
4. Approve the baseline.
5. Publish the artifact.
6. Maintain the document as the product evolves.
7. Archive the document when it is replaced.

## 20. References

TaxiSphere documentation may align with the following standards where useful:

- IEEE 29148, requirements engineering.
- IEEE 1016, software design descriptions.
- OpenAPI 3.1.
- C4 Model.
- UML 2.x.
- BPMN 2.0.
- OWASP ASVS.
- OWASP Top 10.
- Twelve-Factor App.
- Semantic Versioning 2.0.0.

## 21. Glossary

| Term | Definition |
| --- | --- |
| ADR | Architecture Decision Record. A short document that records an important technical decision and its reasoning. |
| Artifact | A project output such as a document, diagram, template, code module, or specification. |
| SaaS | Software as a Service. A software delivery model where customers use a shared hosted platform. |
| Tenant | A taxi association or organization using TaxiSphere with logically isolated data. |
| Traceability | The ability to connect business goals, requirements, design, implementation, and tests. |
| Controlled Document | A document with an owner, version, status, and approval process. |

## Appendix A: Future Standards

The following standards should be created as TaxiSphere matures:

| ID | Document |
| --- | --- |
| TS-COD-001 | Coding Standard |
| TS-GIT-001 | Git Workflow Standard |
| TS-API-001 | REST API Standard |
| TS-SEC-002 | Secure Development Standard |
| TS-TST-002 | Testing Standard |
| TS-OBS-001 | Observability Standard |
| TS-UI-001 | Design System Standard |

