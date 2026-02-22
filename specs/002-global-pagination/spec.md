# Feature Specification: Global Pagination

**Feature Branch**: `002-global-pagination`  
**Created**: 2026-02-22  
**Status**: Draft  
**Input**: User description: "implemente paginação para todos modulos, adicione nas telas de listagem, mas sem alterar o layout das tabelas, apenas adicione a paginação. o default deve ser 10 registros por pagina, com um dropdown com opções para 10,15,20,50,100"

## Clarifications

### Session 2026-02-22
- Q: Qual o formato da resposta para a rota exclusiva de dropdowns não-paginada? → A: Criar uma rota paralela (ex: `/api/{recurso}/todos`) que retorna a coleção completa original sem paginação (Option B) para não quebrar módulos dependentes.

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Global Default Pagination View (Priority: P1)

As a user, I want all listing screens across the system to load data in pages of 10 items by default, so that the system remains fast and responsive regardless of the amount of registered data. The visual appearance of the tables should remain exactly as it is now.

**Why this priority**: Fundamental performance and usability requirement for lists that grow continuously.

**Independent Test**: Access every listing module (Students, Courses, Classes, Subjects, etc.). Verify that a maximum of 10 records are displayed initially and the visual layout (borders, colors, spacing) of the table body is unchanged.

**Acceptance Scenarios**:

1. **Given** I navigate to a module with >10 records (e.g., Alunos), **When** the page loads, **Then** only 10 records are visible and the layout style is exactly identical to the previous unpaginated version.
2. **Given** I navigate to a module with <=10 records, **When** the page loads, **Then** all records are visible on a single page, with the paginator hidden or disabled if appropriate.

---

### User Story 2 - Global Page Size Customization (Priority: P2)

As a user, I want to be able to change how many items I see per page across any list in the system, choosing from standard presets (10, 15, 20, 50, 100), so I can adapt the view to my monitor size or workflow needs.

**Why this priority**: Standard flexibility expected in modern admin dashboards.

**Independent Test**: On any listing screen, interact with the "rows per page" dropdown and verify the list updates to match the selected limit.

**Acceptance Scenarios**:

1. **Given** I am viewing a paginated list, **When** I change the dropdown from 10 to 50, **Then** the table fetches and displays up to 50 records per page.
2. **Given** I select a page size larger than the total number of records, **When** the request completes, **Then** all available records are displayed on page 1.

---

### User Story 3 - Page Navigation Across Modules (Priority: P3)

As a user, I can navigate forward and backward through pages on every module's list, ensuring I can access historical or alphabetically distant records.

**Why this priority**: Completes the core pagination feature set.

**Independent Test**: Click "Next", "Previous", "First", and "Last" on various modules and verify the data displayed changes appropriately based on the page offset.

**Acceptance Scenarios**:

1. **Given** I am on page 1 of a list, **When** I click "Next", **Then** the start index offset shifts by the page size and displays the second logical subset of data.
2. **Given** I am on the last page, **When** I look at the controls, **Then** the "Next" and "Last" buttons are visually disabled.

---

### Edge Cases

- **Empty State**: What happens when a list has zero records? The paginator should ideally remain hidden (`alwaysShowPaginator=false`).
- **Layout Integrity**: Does adding the paginator component shift or break custom CSS classes (`p-datatable-gridlines`, custom widths) applied to specific tables? The implementation must explicitly preserve existing `[tableStyle]`, `styleClass` and column bindings.
- **Search Interaction**: When a user performs a search, does the pagination reset to Page 1? Yes, filtering should always reset the current page index to 0 to avoid empty states if the results are fewer than the current page offset.

## Requirements *(mandatory)*

### Functional Requirements

*Note: Define requirements in terms of business rules and domain entities (Clean Architecture).*

- **FR-001**: The system MUST implement server-side pagination across all persistence adapters and presentation controllers for all core entities (Alunos, Disciplinas, Cursos, Responsáveis, Ano Letivo, Turmas, etc.).
- **FR-002**: The default page size MUST be exactly 10 records across the entire application.
- **FR-003**: The UI MUST provide a dropdown selector for page sizes with the exact options: [10, 15, 20, 50, 100].
- **FR-004**: The UI layout inside the tables MUST NOT be altered. Only the paginator controls wrapper is allowed to be appended to the existing tables.
- FR-005: The system MUST return a standardized DTO structure (e.g., `PaginatedResponse`) for all list API endpoints to ensure frontend consistency.
- **FR-006**: The system MUST provide an explicit non-paginated endpoint (e.g., `GET /api/{recurso}/todos`) for core entities specifically to populate dropdowns across different screens without enforcing pagination on selection lists.

### Key Entities *(include if feature involves data)*

- **All Core Domain Entities**: The implementation affects Aluno, Disciplina, Curso, Responsavel, AnoLetivo, Turma, and potentially any future entity requiring a list view.
- **PaginationResult (Domain) / PaginatedResponse (API)**: The wrapper structure standardizing paginated outputs across all modules.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% of all main module listing views use lazy-loaded server-side pagination.
- **SC-002**: 0 visual regressions occur in the rendering of table headers and rows (verified manually or via visual diffs).
- **SC-003**: Page transitions across all modules complete in under 300ms on server response.
- **SC-004**: The codebase avoids duplicating pagination logic by reusing a common pattern or component setup where possible.
