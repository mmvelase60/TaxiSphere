# Security Policy

TaxiSphere is designed as a multi-tenant SaaS platform, so security and tenant isolation are core architectural requirements.

## Security Principles

- Authenticate every user.
- Authorize every sensitive action.
- Isolate tenant-owned data.
- Validate all external input.
- Log security-relevant actions.
- Avoid exposing secrets in source control.
- Prefer secure defaults over optional security.

## Reporting Security Issues

Do not create public issues for sensitive vulnerabilities.

For now, document suspected security issues privately with the project maintainer until a formal disclosure process is established.

## Tenant Isolation Rule

No user from one taxi association may access another association's operational, financial, driver, vehicle, trip, or reporting data unless a documented platform administration workflow explicitly permits it.
