# Tasks: Global Pagination

**Input**: Design documents from `/specs/002-global-pagination/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/pagination.md, quickstart.md

**Organization**: Tasks are grouped into foundational backend work and then frontend UI changes organized by user story.

## Path Conventions

- **Backend**: `src/main/java/br/com/sdd/controleacademico/`
- **Frontend**: `frontend/src/app/pages/`

## Phase 1: Foundational Backend Updates (Blocking)

**Purpose**: Update all backend modules to support `listarPaginado` so the UI can be implemented.

**⚠️ CRITICAL**: No frontend user story work can begin until this phase is complete for the specific module being worked on.

### 1. Alunos
- [x] T001 Update `AlunoRepositoryPort` to support `listarPaginado` in `application/port/out/AlunoRepositoryPort.java`
- [x] T002 Update `AlunoRepositoryAdapter` to implement `listarPaginado` in `infrastructure/persistence/adapter/AlunoRepositoryAdapter.java`
- [x] T003 Update `ListarAlunosUseCase` and `ListarAlunosService` in `application/port/in/` and `application/usecase/`
- [x] T004 Update `AlunoController` to add paginated GET endpoint in `presentation/rest/AlunoController.java`

### 2. Cursos
- [x] T005 Update `CursoRepositoryPort` to support `listarPaginado` in `application/port/out/CursoRepositoryPort.java`
- [x] T006 Update `CursoRepositoryAdapter` to implement `listarPaginado` in `infrastructure/persistence/adapter/CursoRepositoryAdapter.java`
- [x] T007 Update `ListarCursosUseCase` and `ListarCursosService` in `application/port/in/` and `application/usecase/`
- [x] T008 Update `CursoController` to add paginated GET endpoint in `presentation/rest/CursoController.java`

### 3. Disciplinas
- [x] T009 Update `DisciplinaRepositoryPort` to support `listarPaginado` in `application/port/out/DisciplinaRepositoryPort.java`
- [x] T010 Update `DisciplinaRepositoryAdapter` to implement `listarPaginado` in `infrastructure/persistence/adapter/DisciplinaRepositoryAdapter.java`
- [x] T011 Update `ListarDisciplinasUseCase` and `ListarDisciplinasService` in `application/port/in/` and `application/usecase/`
- [x] T012 Update `DisciplinaController` to add paginated GET endpoint in `presentation/rest/DisciplinaController.java`

### 4. Anos Letivos
- [x] T013 Update `AnoLetivoRepositoryPort` to support `listarPaginado` in `application/port/out/AnoLetivoRepositoryPort.java`
- [x] T014 Update `AnoLetivoRepositoryAdapter` to implement `listarPaginado` with correct sorting in `infrastructure/persistence/adapter/AnoLetivoRepositoryAdapter.java`
- [x] T015 Update `ListarAnosLetivosUseCase` and `ListarAnosLetivosService` in `application/port/in/` and `application/usecase/`
- [x] T016 Update `AnoLetivoController` to add paginated GET endpoint in `presentation/rest/AnoLetivoController.java`

### 5. Turmas
- [x] T017 Update `TurmaRepositoryPort` to support `listarPaginado` in `application/port/out/TurmaRepositoryPort.java`
- [x] T018 Update `TurmaRepositoryAdapter` to implement `listarPaginado` in `infrastructure/persistence/adapter/TurmaRepositoryAdapter.java`
- [x] T019 Update `ListarTurmasUseCase` and `ListarTurmasService` in `application/port/in/` and `application/usecase/`
- [x] T020 Update `TurmaController` to add paginated GET endpoint in `presentation/rest/TurmaController.java`

### 6. Responsáveis
- [x] T021 Update `ResponsavelRepositoryPort` to support `listarPaginado` in `application/port/out/ResponsavelRepositoryPort.java`
- [x] T022 Update `ResponsavelRepositoryAdapter` to implement `listarPaginado` in `infrastructure/persistence/adapter/ResponsavelRepositoryAdapter.java`
- [x] T023 Update `ListarResponsaveisUseCase` and `ListarResponsaveisService` in `application/port/in/` and `application/usecase/`
- [x] T024 Update `ResponsavelController` to add paginated GET endpoint in `presentation/rest/ResponsavelController.java`

---

## Phase 2: User Story 1 - Global Default Pagination View (Priority: P1) 🎯 MVP

**Goal**: Display a maximum of 10 records per page initially across all modules, preserving layout.

**Independent Test**: Access any list and verify that only 10 items are shown.

### Implementation for User Story 1

- [x] T025 [P] [US1] Update `AlunoListComponent` to use lazy pagination in `frontend/src/app/pages/alunos/aluno-list.ts`
- [x] T026 [P] [US1] Update `CursoListComponent` to use lazy pagination in `frontend/src/app/pages/cursos/curso-list.ts`
- [x] T027 [P] [US1] Update `DisciplinaListComponent` to use lazy pagination in `frontend/src/app/pages/disciplinas/disciplina-list.ts`
- [x] T028 [P] [US1] Update `AnoLetivoListComponent` to use lazy pagination in `frontend/src/app/pages/anos-letivos/ano-letivo-list.ts`
- [x] T029 [P] [US1] Update `TurmaListComponent` to use lazy pagination in `frontend/src/app/pages/turmas/turma-list.ts`
- [x] T030 [P] [US1] Update `ResponsavelListComponent` to use lazy pagination in `frontend/src/app/pages/responsaveis/responsavel-list.ts`

**Checkpoint**: US1 complete. All lists display 10 items per page by default.

---

## Phase 3: User Story 2 - Global Page Size Customization (Priority: P2) & User Story 3 - Page Navigation (Priority: P3)

**Goal**: Allow page size adjustment (10, 15, 20, 50, 100) and moving through pages (Next, Previous).

**Independent Test**: Change dropdown values and navigate using paginator controls. 

### Implementation for US2 & US3

- [x] T031 [P] [US2] Ensure all 6 list components have `[rowsPerPageOptions]="[10, 15, 20, 50, 100]"` configured on `<p-table>`
- [x] T032 [P] [US3] Verify `onLazyLoad` handles `event.first` and `event.rows` properly mapped into API queries across all 6 lists

**Checkpoint**: Full pagination functionality completed.

---

## Phase 4: Polish & Cross-Cutting Concerns

**Purpose**: Refinements and edge cases handling.

- [x] T033 Ensure search explicitly resets pagination (`first.set(0)`) across all modules
- [x] T034 Verify `[alwaysShowPaginator]="false"` is applied on all 6 `<p-table>` configurations so it hides when empty
- [x] T035 Cross-check visual rendering to confirm `styleClass` and grid formats remain identical to pre-pagination state

---

## Phase 5: Non-Paginated Endpoints for Dropdowns (Option B)

**Purpose**: Restore functionality to configuration and association screens that depend on full lists for dropdowns.

- [x] T036 Add `/todos` endpoints returning full, unpaginated lists to all core entity controllers (Alunos, Cursos, Disciplinas, Anos Letivos, Turmas, Professores, Responsáveis)
- [x] T037 Update frontend dropdown service calls (`Turmas`, `Grade Horária`, `Disponibilidade`, `Configurações`) to point to `/todos` endpoints

---

## Dependencies & Execution Order

- Phase 1 (Backend APIs) must be completed before Phase 2.
- Phase 2 (UI integration) can be parallelized per module.
- Phases 3 & 4 (Polish & interactions) are implemented as additions on top of Phase 2.

### Parallel Opportunities

- Each module in Phase 1 (Alunos, Cursos, etc.) can be updated in parallel if needed.
- Each UI component in Phase 2 can be developed entirely independently of the others once the backend is ready.

### Implementation Strategy

1. Standardize backend contracts across all repositories and controllers.
2. Go component by component on the frontend and inject the standard primeNG signals structure (as achieved on the Teacher module).
3. Smoke test pagination controls + search combinations.
