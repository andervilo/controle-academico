# Quickstart: Global Pagination

## Module Replication Strategy

Use the `Professor` module as the definitive template. Here's a brief recap:

**Step 1: Domain Definition (Backend)**
Ensure the entity's Port exposes `listarPaginado(int page, int size)` returning a `PaginationResult`. Ensure the `Listar[Entity]UseCase` implements it.

**Step 2: Persistence Setup (Backend)**
In the `RepositoryAdapter`, inject `PageRequest` and map the Spring Data `Page` to `PaginationResult`. **IMPORTANT**: For `AnoLetivo`, order by an applicable field (like `ano` DESC or `dataInicio` ASC) rather than `nome`.

**Step 3: Controller Update (Backend)**
Adjust the REST endpoint mapping (`listarPaginado`) handling `page` and `size`, returning a `PaginatedResponse`.

**Step 4: Table Config (Frontend)**
In each `*-list.ts`:
- Pre-pend new Signals: `totalRecords = signal(0)`, `rows = signal(10)`, `first = signal(0)`.
- Re-wire API integration with dynamic variables `({ params })`.
- Introduce `<p-table [lazy]="true" [paginator]="true" ...>`.
- Keep existing CSS selectors (`tableStyle`).
