# Supermarket Management System (Spring Boot)

Production-ready Spring Boot backend for a **Supermarket Management System** using Java 17, PostgreSQL, JWT auth, layered architecture, and enterprise-grade patterns.

## Tech Stack
- Java 17
- Spring Boot 3.5.0
- Maven
- PostgreSQL
- Spring Data JPA
- Spring Security + JWT
- Validation + Global Exception Handling
- SLF4J Logging

## Architecture
```
controller -> service -> repository -> database
```

## API Base URL
`/api/v1`

## Key APIs
- `POST /api/v1/auth/register`
- `POST /api/v1/auth/login`
- `GET /api/v1/products`
- `POST /api/v1/products`
- `POST /api/v1/billing/create`
- `GET /api/v1/reports/sales`

## Run
```bash
mvn spring-boot:run
```

## Build
```bash
mvn clean verify
```
