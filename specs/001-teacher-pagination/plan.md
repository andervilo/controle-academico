# Implementation Plan: Teacher Listing Pagination

**Branch**: `001-teacher-pagination` | **Date**: 2026-02-21 | **Spec**: [specs/001-teacher-pagination/spec.md](spec.md)
**Input**: Feature specification from `/specs/001-teacher-pagination/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Implement server-side pagination for the teacher listing. The backend will be updated to support `page` and `size` parameters in the REST API, and the frontend will use PrimeNG's Paginator (within the DataTable) to provide the specified page size options (10, 15, 20, 50, 100) and navigation controls.

## Technical Context

**Language/Version**: Java 21, Angular 19
**Primary Dependencies**: Spring Boot 3.3.x, Spring Data JPA, PrimeNG, Signals (Angular)
**Storage**: PostgreSQL
**Testing**: JUnit 5, Spring Boot Test
**Target Platform**: Docker (Nginx + Spring Boot)
**Project Type**: Web Application (Monorepo)
**Performance Goals**: Page transitions < 300ms, API response < 200ms
**Constraints**: Must match Sakai-NG styling, use Clean Architecture ports/adapters.
**Scale/Scope**: Teacher listing (potentially thousands of records).

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- **Clean Architecture**: ✅ Design respects Domain/Application/Infrastructure/Presentation separation. Pagination logic will go through Port.In/UseCase and Port.Out/PersistenceAdapter.
- **Docker-First**: ✅ No new infrastructure requirements.
- **UI Consistency**: ✅ Using PrimeNG/Sakai-NG Table pagination components.
- **State Management**: ✅ Angular Signals used for current page, total count, and rows per page.
- **Database**: ✅ Standard JPA `Pageable` will be used; no manual schema changes needed, but Flyway will be used if any sequence adjustments are needed.

## Project Structure

### Documentation (this feature)

```text
.specify/specs/001-teacher-pagination/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/           # Phase 1 output (/speckit.plan command)
└── tasks.md             # Phase 2 output (/speckit.tasks command)
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
