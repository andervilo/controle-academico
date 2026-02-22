# Data Model: Teacher Pagination

## Entities & DTOs

### PaginatedResponse<T> (Generic DTO)
A wrapper for paginated results returned by the API.

| Field | Type | Description |
|---|---|---|
| content | List<T> | The list of items on the current page |
| totalElements | long | Total number of items in the database |
| totalPages | int | Total number of pages |
| size | int | Number of items per page |
| number | int | Current page number (starting from 0) |

## Validation Rules

- **Page Size**: Must be one of [10, 15, 20, 50, 100]. Default: 10.
- **Page Number**: Must be >= 0.

## State Transitions

### Frontend Pagination State (Signals)
- `currentPage`: Integer signal tracking the active page index.
- `pageSize`: Integer signal tracking selected rows per page.
- `items`: Signal holding the current list of items.
- `totalRecords`: Signal holding total count for paginator calculation.
