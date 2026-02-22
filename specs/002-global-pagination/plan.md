# Implementation Plan: Global Pagination

**Branch**: `002-global-pagination` | **Date**: 2026-02-22 | **Spec**: [specs/002-global-pagination/spec.md](spec.md)
**Input**: Feature specification from `/specs/002-global-pagination/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Expand the server-side pagination architecture (recently implemented for teachers) to all remaining core domain modules (Students, Subjects, Courses, Guardians, Academic Year, Classes). The frontend tables will be updated to use lazy loading with a default size of 10 and options [10, 15, 20, 50, 100], strictly preserving the existing visual layout inside the tables.

## Technical Context

**Language/Version**: Java 21, Angular 19  
**Primary Dependencies**: Spring Boot 3.3.x, Spring Data JPA, PrimeNG, Signals (Angular)  
**Storage**: PostgreSQL  
**Testing**: JUnit 5, Spring Boot Test  
**Target Platform**: Docker (Nginx + Spring Boot)  
**Project Type**: Web Application (Monorepo)  
**Performance Goals**: Page transitions < 300ms, API response < 200ms  
**Constraints**: Must NOT alter internal table layout (keep existing styles, borders, typography). Must use Clean Architecture ports/adapters.  
**Scale/Scope**: System-wide update affecting 6+ domain modules.

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- **Clean Architecture**: ✅ The design leverages existing `PaginationResult` domain models and extends it through standard Use Cases and Adapters.
- **Docker-First**: ✅ No new infrastructure requirements.
- **UI Consistency**: ✅ Strictly maintains existing Sakai-NG table layouts while appending the standard PrimeNG paginator.
- **State Management**: ✅ Uses Angular Signals for pagination state (`rows`, `first`, `totalRecords`).
- **Database**: ✅ Uses standard Spring Data `Pageable`; no Flyway migration required for schema.

## Project Structure

### Documentation (this feature)

```text
.specify/specs/002-global-pagination/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── data-model.md        # Phase 1 output
├── quickstart.md        # Phase 1 output
├── contracts/           # Phase 1 output
└── tasks.md             # Phase 2 output
```

### Source Code (repository root)

```text
src/main/java/br/com/sdd/controleacademico/
├── domain/              # Model, Exception, Port.Out
├── application/         # Port.In, UseCase
├── infrastructure/      # Adapter.Persistence, Config
└── presentation/        # Rest, Rest.DTO

frontend/src/app/
├── pages/               # Feature-specific components
├── layout/              # Structural components
└── service/             # API communication services
```

**Structure Decision**: Standard Java/Angular monorepo structure.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| None | N/A | N/A |
