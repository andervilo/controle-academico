# Research: Global Pagination

## Architecture Decision

Because pagination was already successfully introduced via the `001-teacher-pagination` feature, the technical model is completely validated and adopted as the system's standard pattern.

## Implementation Pattern

To achieve global pagination without introducing unexpected behavior or degrading performance, we will strictly follow the pattern established in the `Professor` module:

**Backend (Java/Spring Boot)**
1. **Repository/Port Layer**: Add `PaginationResult<DomainEntity> listarPaginado(int page, int size);` interface.
2. **Adapter Layer**: Implement using Spring Data JPA's `PageRequest.of(page, size, Sort.by("nome"))`.
3. **Use Case Layer**: Expose `listarPaginado` through the service interface.
4. **Controller Layer**: Accept `@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size`, mapping the domain result into `PaginatedResponse<ResourceResponse>`.

**Frontend (Angular/PrimeNG)**
1. **Template**:
   - `[lazy]="true"`
   - `(onLazyLoad)="onLazyLoad($event)"`
   - `[rows]="rows()"`
   - `[totalRecords]="totalRecords()"`
   - `[paginator]="true"`
   - `[alwaysShowPaginator]="false"`
   - `[first]="first()"`
   - `[rowsPerPageOptions]="[10, 15, 20, 50, 100]"`
2. **Signals State**: Add `totalRecords`, `rows`, and `first`.
3. **Methods**: Implement `onLazyLoad` extracting `event.first` and `event.rows`, translating them to `page` and `size` parameters for the `http.get` call.

## Risk Assessment

The primary risk involved is altering the visual layout (`<p-table>`) unintentionally.
- **Mitigation**: The update to frontend templates will explicitly target ONLY pagination directives. Attributes controlling visual design like `[tableStyle]`, specific `styleClass="p-datatable-gridlines"`, or arbitrary `<th>` configurations must remain exactly as they are currently. We will verify the `[tableStyle]` property is not overridden.
