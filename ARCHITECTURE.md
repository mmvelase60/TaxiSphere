# TaxiSphere Architecture

TaxiSphere starts as a modular monolith and is designed to become microservice-ready as product and operational complexity grows.

## Architecture Goals

- Support multiple independent tenants from one deployment.
- Keep tenant data logically isolated.
- Organize backend modules around business capabilities.
- Keep APIs stable, documented, and testable.
- Support cloud deployment with Docker, Kubernetes, and Azure.
- Build observability into the platform from the beginning.

## Initial Architecture Style

The Version 1 architecture is a modular monolith:

- One deployable backend application.
- Clear internal modules by business capability.
- Shared database with logical tenant isolation.
- REST APIs documented with OpenAPI.
- Angular web frontend.

## Future Direction

Modules may be extracted into services when there is a strong operational or scaling reason. Service extraction requires an ADR.

## Key References

- `docs/14-ADR/ADR-001-Multi-Tenant-SaaS.md`
- `docs/14-ADR/ADR-002-Modular-Monolith-Architecture.md`
- `docs/14-ADR/ADR-003-Technology-Stack-Baseline.md`
