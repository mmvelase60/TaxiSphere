# Backend API

TaxiSphere API is the Spring Boot backend for the TaxiSphere Enterprise Mobility Platform.

## Local Authentication

The database migration seeds core security roles only. It does not seed a static administrator password.

For local development, create the first platform administrator by enabling the identity bootstrap runner with environment variables:

```text
TAXISPHERE_IDENTITY_BOOTSTRAP_ENABLED=true
TAXISPHERE_IDENTITY_BOOTSTRAP_EMAIL=platform-admin@taxisphere.local
TAXISPHERE_IDENTITY_BOOTSTRAP_PASSWORD=<choose-a-local-password>
```

After startup, use the configured email and password with:

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "platform-admin@taxisphere.local",
  "password": "<your-local-password>"
}
```

Successful login returns a Bearer JWT access token.
