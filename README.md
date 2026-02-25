# ActorApi Mini Project

## Overview
**ActorApi** is a Spring Boot RESTful API for managing actor information.  
It implements **CRUD operations** and **filtering by remuneration or category**.  
The API is secured, uses **DTOs (VO classes)**, has **global exception handling**, **logging**, and is documented with **Swagger UI**.

---

## Features

- **Security**: Spring Security for role-based or basic authentication.  
- **CRUD Operations**: Create, Read, Update, Delete actors.  
- **Batch Operations**: Register multiple actors at once.  
- **Filtering**: Fetch actors by remuneration range or category.  
- **Exception Handling**: Global handler with custom exceptions like `ActorNotFoundException`.  
- **Logging**: SLF4J logging for start/end, info, debug, and warnings.  
- **Swagger Integration**: Test APIs easily via Swagger UI.

---

## Tech Stack

- Java 17+  
- Spring Boot 3.x  
- Spring Security  
- Spring Data JPA / Hibernate  
- MySQL / H2 (or any RDBMS)  
- SLF4J + Logback  
- Swagger / OpenAPI 3

---

## API Endpoints

| HTTP Method | Endpoint | Description |
|------------|---------|------------|
| POST | `/save` | Register a single actor |
| POST | `/saveAll` | Register multiple actors |
| GET | `/all` | Retrieve all actors |
| GET | `/byId/{id}` | Retrieve actor by ID |
| GET | `/byremunaration/{start}/{end}` | Get actors by remuneration range |
| PUT | `/update` | Update actor details |
| PATCH | `/update/{aid}/{percent}` | Update actor remuneration by percentage |
| DELETE | `/delete/{id}` | Delete actor by ID |
| DELETE | `/delete/{cat1}/{cat2}/{cat3}` | Delete actors by category |

> Swagger UI is available at: `http://localhost:8987/ActorAPI-Swagger/swagger-ui/index.html`

---

## Exception Handling

- **ActorNotFoundException**: Thrown for invalid actor ID.  
- **GlobalExceptionHandler**: Handles exceptions and returns meaningful HTTP responses.  
- **HTTP Status Codes**:  
  - `200 OK` – Success  
  - `201 Created` – Resource created  
  - `404 Not Found` – Actor not found  
  - `400 Bad Request` – Invalid input  

---

## Logging

- **Method Start/End Logging** for all service methods.  
- **Debug Logs** for batch operations (optional).  
- **Info Logs** for successful CRUD operations.  
- **Warn Logs** for invalid operations or missing actors.  
- Uses **SLF4J `{}` placeholders** for efficient logging.

---
