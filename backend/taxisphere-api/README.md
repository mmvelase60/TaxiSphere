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

## Test

```text
mvn test
```

## Health Endpoint

```text
GET /api/v1/platform/health
```

## Notes

Maven was not available on the current machine when this foundation was created, so build verification should run after Maven is installed or after a Maven wrapper is added.
