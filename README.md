# ASM ERP — Backend-First Operations Management API

> A production-style Spring Boot backend for a sewing machine business to manage **repairs, inventory, sales, customers, and admin operations** through secure JWT APIs.

---

## Project Summary

I built **ASM ERP** as a backend-focused system to digitize core business workflows that are usually run manually in small and mid-sized shops.  
The API is designed around real operations use cases:

- **Repair lifecycle tracking** with strict status transitions (`ACCEPTED → IN_PROGRESS → REPAIRED → RETURNED`)
- **Inventory management** with low-stock / restock alerts
- **Sales recording** with automatic stock deduction
- **Role-based user access** for admins and customers
- **JWT authentication** with logout token blacklisting
- **File upload support** for product images

If you are backend-oriented, this project demonstrates practical service-layer design, business-rule enforcement, secure API design, and data modeling in Spring Boot.

---

## Why this project matters

Most SMB tools fail because they focus only on UI screens, but operational correctness is a backend problem. This project emphasizes:

- **State safety**: invalid repair transitions are blocked at service level
- **Data integrity**: sales cannot be recorded beyond available stock
- **Security boundaries**: admin endpoints are isolated from public endpoints
- **Scalable structure**: controller → service → repository layering with DTO mapping

---

## Tech Stack

- **Java 21**
- **Spring Boot 4**
- **Spring Web / Validation / Security**
- **Spring Data JPA + Hibernate**
- **PostgreSQL**
- **JWT (jjwt)** for auth tokens
- **Maven** for build lifecycle
- **Docker** support for deployment

---

## Core Domain Modules

### 1) Authentication & User Management
- Signup / login for customers
- Admin-driven user creation (admin or customer)
- Profile read/update (`/api/auth/me`)
- Password hashing with BCrypt
- JWT generation and request filtering
- Token blacklist on logout

### 2) Repair Management
- Create repair jobs from customer phone + machine details
- Track timestamps per milestone (`acceptedAt`, `inProgressAt`, `repairedAt`, `returnedAt`)
- Controlled status transition rules
- Undo mechanism for status changes (short business-safe window)
- Admin and customer-specific repair views

### 3) Product & Inventory
- Product CRUD-style admin operations
- Category-specific data validation (price vs WhatsApp-link categories)
- Inventory threshold and `needsRestock` flag logic
- Public product catalog endpoints
- Product image upload and static file serving

### 4) Sales
- Admin sale entry endpoint
- Stock auto-decrement during sale creation
- Validation preventing overselling
- Sale data available for dashboard analytics

### 5) Admin Dashboard Insights
- Total repairs
- Pending / in-progress / repaired breakdown
- Low-stock product counts
- Inventory alerts

---

## API Surface (High-level)

### Public / Customer-facing
- `POST /api/auth/signup`
- `POST /api/auth/login`
- `POST /api/auth/logout`
- `GET /api/auth/me`
- `PATCH /api/auth/me`
- `GET /api/products`
- `GET /api/products/category?category=...`
- `GET /api/products/{id}`
- `GET /api/repairs/my`

### Admin-only
- `/api/admin/users/**`
- `/api/admin/products/**`
- `/api/admin/sales/**`
- `/api/admin/dashboard/**`

### Repair Operations
- `POST /api/repairs`
- `PUT /api/repairs/{id}/status`
- `PUT /api/repairs/{id}/final-price`
- `PUT /api/repairs/{id}/undo-status`
- `GET /api/repairs`
- `GET /api/repairs/{id}`
- `GET /api/repairs/customer/{customerId}`
- `GET /api/repairs/by-phone?phone=...`

---

## Security Model

- JWT required for protected routes
- Publicly permitted paths:
  - `/api/auth/**`
  - `/api/products/**`
  - `/uploads/**`
- All `/api/admin/**` routes require `ROLE_ADMIN`
- CORS configured for local frontend ports + deployed frontend origin
- CSRF disabled for token-based API interaction

---

## Project Structure

```text
src/main/java/com/business/OperationsManagement/
├── config/        # Spring Security + Web resource config
├── controller/    # REST endpoints
├── dto/           # Request/response contracts
├── entity/        # JPA entities
├── enums/         # Domain enums
├── exception/     # Custom exceptions + global handlers
├── repository/    # Spring Data repositories
├── security/      # JWT utility, auth filter, token blacklist
└── service/       # Business logic and mapping
```

---

## Running Locally

### Prerequisites
- Java 21
- Maven 3.9+
- PostgreSQL

### Environment Variables
Set these before startup:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET` (use a long, strong secret)
- `PORT` (optional; defaults to `8080`)

### Start the app

```bash
./mvnw spring-boot:run
```

### Run tests

```bash
./mvnw test
```

---

## Docker

Build and run:

```bash
./mvnw clean package

docker build -t asm-erp-api .
docker run -p 8080:8080 \
  -e DB_URL=... \
  -e DB_USERNAME=... \
  -e DB_PASSWORD=... \
  -e JWT_SECRET=... \
  asm-erp-api
```

---

## Backend Engineering Highlights (for recruiters / hiring managers)

- Implemented **RBAC with JWT** and a custom auth filter
- Enforced **repair workflow transitions** as explicit domain rules
- Added **inventory-aware sales logic** with stock safety checks
- Built **modular service layer** with DTO mapping and repository abstraction
- Supported **multipart media uploads** and file-based static serving
- Produced a backend ready for integration with modern SPA frontends

---


## Future Enhancements

- Refresh token strategy and token persistence
- Redis-backed blacklist and caching
- Pagination + filtering standards for all list APIs
- OpenAPI/Swagger docs
- Audit logs for admin actions
- Integration tests with Testcontainers
- CI/CD pipeline with quality gates
