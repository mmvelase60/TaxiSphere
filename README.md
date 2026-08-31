# TaxiSphere Enterprise Mobility Platform

TaxiSphere is a cloud-native, multi-tenant SaaS platform for taxi associations, designed to digitize rank operations, fleet management, dispatch, routes, trips, finance, reporting, and future passenger services.

## Product Direction

The platform is being built as an enterprise reference application with strong documentation, traceable requirements, architecture decision records, and a practical path from modular monolith to cloud-native deployment.

## Current Focus

Release `0.1.0` is the foundation release. It establishes the project structure, documentation standards, engineering handbook, architecture decisions, templates, and GitHub project conventions before backend and frontend implementation begins.

## Repository Structure

```text
.github/        GitHub workflows and collaboration templates
assets/         Branding, diagrams, icons, and reusable visual assets
backend/        Spring Boot backend workspace
decisions/      Project-level decision records and notes
docker/         Local container configuration
docs/           Controlled documentation suite
frontend/       Angular frontend workspace
infrastructure/ Infrastructure design and automation
kubernetes/     Kubernetes manifests and deployment assets
research/       Market, domain, and technical research
samples/        Sample data and example payloads
scripts/        Project automation scripts
terraform/      Infrastructure as Code
tools/          Project utilities and generators
```

## Documentation

Start here:

- `docs/00-Governance/Standards/TS-STD-001-Documentation-and-Branding-Standard.md`
- `docs/13-Engineering-Handbook/TS-ENG-001.md`
- `docs/14-ADR/`
- `docs/16-Templates/`

## Technology Baseline

The exact implementation versions will be confirmed when the backend and frontend projects are initialized.

- Backend: latest stable Java release supported by the selected Spring Boot version.
- Frontend: latest stable Angular major release available at implementation time.
- Database: MySQL initially, with PostgreSQL readiness considered in design.
- Deployment: Docker first, then Kubernetes and Azure.

## Status

TaxiSphere is in Sprint 0: foundation and governance.
