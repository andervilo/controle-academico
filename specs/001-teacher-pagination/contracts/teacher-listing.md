# API Contract: Listar Professores (Paginated)

## Endpoint: GET /api/v1/professores

### Description
Returns a paginated list of teachers.

### Query Parameters

| Parameter | Type | Default | Required | Description |
|---|---|---|---|---|
| page | int | 0 | No | The page number (0-indexed) |
| size | int | 10 | No | Number of records per page |

### Response: 200 OK

```json
{
  "content": [
    {
      "id": "uuid",
      "nome": "Professor Name",
      "cpf": "123.456.789-00",
      "email": "prof@email.com",
      "telefone": "(11) 99999-9999"
    }
  ],
  "totalElements": 25,
  "totalPages": 3,
  "size": 10,
  "number": 0
}
```

### Error Responses

- **400 Bad Request**: Invalid pagination parameters (e.g., negative page).
