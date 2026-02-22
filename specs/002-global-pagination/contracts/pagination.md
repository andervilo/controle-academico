# API Contract: Global Listing (Paginated)

## Endpoints

The following `GET` endpoints will be updated to accept pagination parameters and return the `PaginatedResponse<T>` structure:

- `/api/v1/alunos`
- `/api/v1/cursos`
- `/api/v1/disciplinas`
- `/api/v1/anos-letivos`
- `/api/v1/turmas`
- `/api/v1/responsaveis`

## Query Parameters

| Parameter | Type | Default | Required | Description |
|-----------|------|---------|----------|-------------|
| page | int | 0 | No | The page number (0-indexed) |
| size | int | 10 | No | Number of records per page |

## Response Structure (200 OK)

```json
{
  "content": [
    // Array of DTO objects specific to the module
  ],
  "totalElements": 25,
  "totalPages": 3,
  "size": 10,
  "number": 0
}
```

## Backward Compatibility
Existing tests or integrations expecting an array directly (`[]`) MUST be updated to expect the `.content` array inside the above object structure.
