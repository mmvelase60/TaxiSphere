---
document_id: TS-BCK-002
title: TaxiSphere Backend Build and Run Tooling
version: 0.1.0
status: Draft
classification: Internal Engineering
owner: Backend Engineering Office
project: TaxiSphere Enterprise Mobility Platform
last_updated: 2026-08-31
---

# TaxiSphere Backend Build and Run Tooling

## Executive Summary

This document records the first backend tooling baseline for TaxiSphere. It adds Docker build support, local Docker Compose infrastructure, environment examples, and backend CI structure.

## 1. Tooling Added

| File | Purpose |
| --- | --- |
| `backend/taxisphere-api/Dockerfile` | Builds and runs the Spring Boot API container. |
| `backend/taxisphere-api/.dockerignore` | Excludes local build and IDE noise from Docker context. |
| `docker/docker-compose.yml` | Runs MySQL and the API together for local integration. |
| `backend/taxisphere-api/.env.example` | Documents expected local backend environment variables. |
| `.github/workflows/backend-ci.yml` | Adds CI baseline for backend test execution. |
| `.mvn/wrapper/maven-wrapper.properties` | Records Maven wrapper distribution settings. |

## 2. Important Toolchain Note

The Maven wrapper scripts are included as placeholders, but the wrapper JAR is not committed yet. The local machine does not currently have Maven installed, so the next step is to either:

- Install Maven and run `mvn -N wrapper:wrapper` inside `backend/taxisphere-api`.
- Generate the wrapper from an environment where Maven is already available.

Docker builds use the official Maven builder image, so they do not require Maven to be installed locally. Once the wrapper JAR exists, `mvnw.cmd test` can run without requiring global Maven.

## 3. Local Run Targets

Run backend tests after Maven or wrapper setup:

```text
cd backend/taxisphere-api
mvn test
```

Run local infrastructure:

```text
cd docker
docker compose up --build
```

## 4. Next Steps

- Add the actual Maven wrapper JAR through Maven wrapper generation.
- Add MySQL driver dependency when persistence work begins.
- Add Flyway when database migrations begin.
- Add Testcontainers when integration tests begin.
