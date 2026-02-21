<!--
Sync Impact Report:
- Version change: 1.0.0 -> 1.1.0
- List of modified principles:
  - Added: I. Clean Architecture Strictness
  - Added: II. Docker-First Infrastructure
  - Added: III. Sakai-NG & PrimeNG Visual Identity
  - Added: IV. Reactive State with Angular Signals
  - Added: V. Database Evolution with Flyway
- Added sections: Technical Constraints, Development Workflow
- Templates requiring updates:
  - .specify/templates/plan-template.md (✅ updated)
  - .specify/templates/spec-template.md (✅ updated)
  - .specify/templates/tasks-template.md (✅ updated)
- Follow-up TODOs: None.
-->

# Controle Acadêmico SDD Constitution

## Core Principles

### I. Clean Architecture Strictness (NON-NEGOTIABLE)
The project MUST maintain clear boundaries between Domain, Application, Infrastructure, and Presentation. 
- **Domain**: Pure business logic, no external dependencies. Entities and Domain Exceptions live here.
- **Application**: Ports (interfaces) and Use Cases coordinating domain logic.
- **Infrastructure**: Adapters for technical details (DB persistence, Repository implementations, external APIs).
- **Presentation**: REST Controllers and DTOs (Request/Response Records).
**Rationale**: Ensures the core logic is testable, maintainable, and decoupled from framework volatility.

### II. Docker-First Infrastructure
The environment (PostgreSQL, Spring Boot Backend, Angular Frontend) MUST be managed via Docker Compose. Setup MUST be reproducible with `docker-compose up`.
**Rationale**: Eliminates environment-specific bugs and ensures parity between local and production stages.

### III. Sakai-NG & PrimeNG Visual Identity
UI development MUST strictly adhere to the Sakai-NG design system and PrimeNG component library.
- Use PrimeIcons for icons.
- Use PrimeFlex/Tailwind ONLY for layout/spacing utilities.
- Maintain consistency with the Sakai-NG layout template.
**Rationale**: Maintains professional UI consistency and leverages the established design system assets.

### IV. Reactive State with Angular Signals
The Angular frontend MUST utilize Signals for state management and reactivity. Signals should be the primary mechanism for UI state, with RxJS reserved for data streams (HTTP).
**Rationale**: Optimized change detection and cleaner reactive logic.

### V. Database Evolution with Flyway
ALL database schema changes MUST be performed via SQL migration scripts in `src/main/resources/db/migration`. Manual DB schema manipulation is forbidden.
**Rationale**: Ensures versioned, repeatable, and automated database schema management.

## Technical Constraints

### Backend (Java/Spring)
- **Runtime**: Java 21 LTSC.
- **Framework**: Spring Boot 3.3.x.
- **Persistence**: Spring Data JPA + Hibernate + PostgreSQL.
- **Mapping**: MapStruct for DTO <-> Domain <-> Entity conversion.
- **Persistence Pattern**: Repository Adapter pattern (Port/Adapter) to decouple domain from JPA entities.

### Frontend (Angular)
- **Framework**: Angular 19.
- **Design**: PrimeNG (Sakai-NG).
- **Icons**: PrimeIcons.

## Development Workflow

- **Feature Specification**: Every feature starts with a `spec.md`.
- **Implementation Planning**: `plan.md` must be created and verified against these principles.
- **Task Tracking**: `tasks.md` must be generated for atomic task tracking.

## Governance

- This Constitution supersedes ad-hoc development decisions.
- Amendments MUST be documented with a version bump.
- Compliance is verified during the `plan.md` "Constitution Check".

**Version**: 1.1.0 | **Ratified**: 2026-02-21 | **Last Amended**: 2026-02-21
