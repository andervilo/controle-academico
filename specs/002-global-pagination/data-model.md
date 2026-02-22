# Data Model: Global Pagination

## Entities & DTOs

### Reused Global Entities
The project already defines the wrapper logic:

1. **`PaginationResult<T>` (Domain)**
   - `content`: List<T>
   - `totalElements`: long
   - `totalPages`: int
   - `size`: int
   - `number`: int

2. **`PaginatedResponse<T>` (Presentation)**
   - `content`: List<T>
   - `totalElements`: long
   - `totalPages`: int
   - `size`: int
   - `number`: int

## Affected Persistence Adapters
The following adapters will have a new method (e.g. `listarPaginado` and `PageRequest`) mapping from `org.springframework.data.domain.Page`:
- AlunoRepositoryAdapter
- CursoRepositoryAdapter
- DisciplinaRepositoryAdapter
- AnoLetivoRepositoryAdapter
- TurmaRepositoryAdapter
- ResponsavelRepositoryAdapter

### Validation
Page sizes enforced on `<p-table>`: `[10, 15, 20, 50, 100]`. Default: `10`.
Backend will gracefully handle oversized pages if configured, but default `@RequestParam size` is `10`.
