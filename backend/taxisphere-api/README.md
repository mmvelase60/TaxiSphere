# TaxiSphere API

This folder contains the Spring Boot backend for the TaxiSphere Enterprise Mobility Platform.

## Current Baseline

- Java: 17 for the initial local foundation because the current development machine has Java 17 installed.
- Framework: Spring Boot 3.3.5.
- Build tool: Maven.
- Package root: `com.spheretech.taxisphere`.

The project can be upgraded to a newer Java LTS after the local toolchain and Spring Boot support are aligned.

## Run

```text
mvn spring-boot:run
```

Or use Docker Compose from the repository root:

```text
cd docker
docker compose up --build
```

## Test

```text
mvn test
```

## Health Endpoint

```text
GET /api/v1/platform/health
```

## Notes

Maven was not available on the current machine when this foundation was created, so direct local build verification should run after Maven is installed or after a complete Maven wrapper is added. Docker builds use a Maven builder image and do not require Maven to be installed locally.
