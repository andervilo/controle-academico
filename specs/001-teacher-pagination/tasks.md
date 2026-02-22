# Tasks: Teacher Listing Pagination

**Input**: Design documents from `/specs/001-teacher-pagination/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: Tests are NOT requested in the spec, so we will focus on implementation and manual verification.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Path Conventions

- **Backend (Java)**: `src/main/java/br/com/sdd/controleacademico/`
- **Frontend (Angular)**: `frontend/src/app/`
- **Database Migrations**: `src/main/resources/db/migration/`

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Project initialization and basic structure

- [x] T001 Create `PaginatedResponse` DTO in `presentation/rest/dto/PaginatedResponse.java`
- [x] T002 Update `ProfessorResponse` if needed or ensure it matches `teacher-listing.md` contract

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Core infrastructure that MUST be complete before ANY user story can be implemented

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [x] T003 Update `ProfessorRepositoryPort` to support `Pageable` in `application/port/out/ProfessorRepositoryPort.java`
- [x] T004 Update `ProfessorRepositoryAdapter` to implement paginated listing in `infrastructure/persistence/adapter/ProfessorRepositoryAdapter.java`
- [x] T005 Update `ListarProfessoresUseCase` to accept pagination parameters in `application/port/in/ListarProfessoresUseCase.java`
- [x] T006 Update `ListarProfessoresService` to handle pagination in `application/usecase/ListarProfessoresService.java`
- [x] T007 Update `ProfessorController` to expose paginated GET endpoint in `presentation/rest/ProfessorController.java`

**Checkpoint**: Backend foundation ready - frontend implementation can now begin

---

## Phase 3: User Story 1 - Default Paginated View (Priority: P1) 🎯 MVP

**Goal**: Display the first 10 teachers by default using server-side pagination.

**Independent Test**: Access the teacher list and verify that only 10 teachers are displayed initially.

### Implementation for User Story 1

- [x] T008 [US1] Add `totalRecords`, `rows`, and `first` signals to `ProfessorListComponent` in `frontend/src/app/pages/professores/professor-list.ts`
- [x] T009 [US1] Update `load()` method to support pagination parameters in `frontend/src/app/pages/professores/professor-list.ts`
- [x] T010 [US1] Configure `p-table` for lazy loading and default pagination in `frontend/src/app/pages/professores/professor-list.ts`
- [x] T011 [US1] Implement `onLazyLoad` event handler to fetch paginated data

**Checkpoint**: User Story 1 complete - basic pagination working.

---

## Phase 4: User Story 2 - Changing Page Size (Priority: P2)

**Goal**: Allow users to select different page sizes (10, 15, 20, 50, 100).

**Independent Test**: Change the dropdown value and verify the number of records displayed updates correctly.

### Implementation for User Story 2

- [x] T012 [US2] Update `p-table` with `[rowsPerPageOptions]="[10, 15, 20, 50, 100]"` in `frontend/src/app/pages/professores/professor-list.ts`
- [x] T013 [US2] Ensure `onLazyLoad` correctly handles `rows` change from the dropdown

**Checkpoint**: User Story 2 complete - page size selection active.

---

## Phase 5: User Story 3 - Page Navigation (Priority: P3)

**Goal**: Navigate between pages using the paginator controls.

**Independent Test**: Click "Next" and verify the second page of teachers is loaded.

### Implementation for User Story 3

- [x] T014 [US3] Ensure `onLazyLoad` correctly updates the `first` offset and `page` parameter for backend requests
- [x] T015 [US3] Verify navigation button states (disabled/enabled) match the current page and total records

**Checkpoint**: All user stories functional.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Improvements and edge case handling.

- [x] T016 Handle "Empty State" in paginator visibility
- [x] T017 Ensure `loading` signal is correctly managed during all pagination transitions
- [x] T018 Documentation updates and final verification against `SC-001` to `SC-004`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: Defines the DTOs used by all other phases.
- **Foundational (Phase 2)**: All backend work. MUST be complete before Phase 3.
- **User Stories (Phase 3-5)**: UI implementation.
- **Polish (Phase 6)**: Final touch-ups.

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Backend Implementation (Phases 1 & 2).
2. Implement Basic Lazy Loading in Frontend (Phase 3).
3. **STOP and VALIDATE**: Verify that only 10 teachers load and the table shows it's in "lazy" mode.

### Incremental Delivery

1. Foundation ready -> API supports `?page=0&size=10`.
2. US1 -> UI shows page 0.
3. US2/US3 -> Full control over pagination interactive features.
