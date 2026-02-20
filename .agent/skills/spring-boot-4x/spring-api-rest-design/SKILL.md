---
name: "spring-api-rest-design"
description: "Padroniza design REST (endpoints, DTOs, paginação, filtros, idempotência) e tratamento de erros com Problem Details."
---

# API REST — Contratos, padrões e consistência

## Convenções
- Base: `/api/v1` (use versionamento apenas quando necessário)
- Recursos no plural: `/orders`, `/customers/{id}`
- HTTP:
  - GET 200
  - POST 201 + `Location`
  - PUT/PATCH 200/204
  - DELETE 204
- Paginação: `page`, `size`, `sort=field,asc|desc`
- Filtro via query params, evitando explosão de endpoints

## DTOs
- `*Request` para entrada
- `*Response` para saída
- Nunca exponha entidade JPA
- Validação: `@Valid` + constraints no request DTO

## Respostas
- Consistência de campos (camelCase)
- IDs e timestamps claros
- Não retornar dados sensíveis

## Erros — Problem Details
- Content-Type: `application/problem+json`
- Campos mínimos: `type`, `title`, `status`, `detail`, `instance`
- Para validação: `errors: [{ field, message }]`

## Idempotência (quando aplicável)
- Header `Idempotency-Key` em POST críticos
- Persistir chave + resultado (TTL) para evitar duplicidade

## Checklist
- [x] OpenAPI (Swagger) atualizado com docs em todos endpoints
- [x] Paginação e sorting padronizados
- [x] Erros padronizados (Problem Details)
- [x] ControllerAdvice cobrindo 400/401/403/404/409/500
