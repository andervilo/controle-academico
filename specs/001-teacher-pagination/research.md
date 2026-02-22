# Research: Teacher Listing Pagination

## Decisions

### 1. Server-Side Pagination Components
- **Backend Component**: Use Spring Data JPA's `Pageable` and `Page` interface.
- **Frontend Component**: Use PrimeNG `p-table` with `[lazy]="true"`.
- **Rationale**: These are standard, well-integrated solutions for Spring Boot and Angular/PrimeNG that handle complex pagination requirements efficiently.

### 2. API Contract for Pagination
- **Parameters**: `page` (0-indexed) and `size`.
- **Response Structure**:
  ```json
  {
    "content": [...],
    "totalElements": 100,
    "totalPages": 10,
    "size": 10,
    "number": 0
  }
  ```
- **Rationale**: Matching Spring Data's `Page` object structure directly in the DTO simplifies mapping and provides all necessary metadata to the frontend.

### 3. Clean Architecture Integration
- **Presentation**: `ProfessorController` receives `page` and `size` via `@RequestParam`.
- **Application**: `ListarProfessoresUseCase` accepts a `Pageable` or simple `page`/`size` integers.
- **Infrastructure**: `ProfessorRepositoryAdapter` maps parameters to `PageRequest.of(page, size)` and calls the Spring Data Repository.
- **Rationale**: Maintains strict layer separation while propagating pagination needs from the edge (API) to the source (DB).

## Best Practices

### Spring Data JPA
- Use `PageRequest.of(page, size, Sort.by("nome"))` to ensure consistent ordering.
- Always provide a default sort order to avoid non-deterministic result sets when paginating.

### PrimeNG DataTable (Lazy Loading)
- Set `[lazy]="true"` and `[totalRecords]="totalRecords"`.
- Implement `onLazyLoad(event: TableLazyLoadEvent)` to trigger backend requests.
- Use `rowsPerPageOptions` to provide the requested [10, 15, 20, 50, 100] choices.

## Alternatives Considered

### 1. Client-Side Pagination
- **Decision**: Rejected.
- **Rationale**: While simpler (current state), it doesn't scale for large datasets and was explicitly requested as a change (pagination to list).

### 2. Offset-Based vs Cursor-Based Pagination
- **Decision**: Offset-based.
- **Rationale**: Offset-based is standard for administrative listings where jumping to a specific page is important. Cursor-based is better for infinite scrolling but overkill for this use case.
