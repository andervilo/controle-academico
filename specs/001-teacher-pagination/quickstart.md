# Quickstart: Teacher Pagination

## Backend Implementation

1. Update `ListarProfessoresUseCase` to accept `page` and `size`.
2. Update `ProfessorRepositoryPort` to accept `page` and `size`.
3. Update `ProfessorRepositoryAdapter` to use `PageRequest` and return a custom `PaginatedResponse`.
4. Update `ProfessorController` to expose the new parameters.

## Frontend Implementation

1. Modify `ProfessorListComponent` to set `[lazy]="true"` on the `p-table`.
2. Bind `rows`, `totalRecords`, and `loading` signals to the table.
3. Use the `onLazyLoad` event to call the service with current page/size.
4. Update `items` signal with `res.content`.

## Testing

### Manual Test
1. Access the Professors page.
2. Verify only 10 items are loaded initially.
3. Change to 15 items per page using the dropdown.
4. Navigate to the next page and verify data changes.

### Automated Test
- Unit test for `ProfessorRepositoryAdapter` verifying `Pageable` is correctly passed to the Spring Repository.
- Integration test for `ProfessorController` verifying the pagination parameters work as expected.
