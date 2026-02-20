---
name: "spring-persistence-jpa-flyway"
description: "Boas práticas de persistência com JPA/Hibernate, transações, performance (N+1), migrações Flyway e modelagem de dados."
---

# Persistência — JPA/Hibernate + Flyway

## Regras
- `@Transactional` no nível application (use case/service), não no controller.
- Leituras: `@Transactional(readOnly = true)`
- Evitar N+1: fetch join, entity graph, projections, paginação
- Índices: baseados em consultas reais

## Modelagem
- ID público: UUID (ou outro padrão do time)
- Auditoria: createdAt/updatedAt
- Soft Delete: Exclusão lógica (campo `deleted` ou `deleted_at`) em vez de física (Hard Delete).
- Constraints no banco + validação no DTO (dupla barreira)

## Flyway
- Padrão: `V1__init.sql`, `V2__add_index.sql`
- Migrações sempre forward-only
- Não editar migração aplicada em ambientes compartilhados

## Repositórios
- Interfaces (ports) em `application.port.out`
- Implementações Spring Data em `infrastructure.persistence`
- Preferir retornos explícitos; evitar Optional aninhado em cascata

## Performance
- Paginação sempre em listas grandes
- Projections para endpoints de listagem
- Batch size quando necessário
- Ajustar lazy/eager com intenção (não por acidente)

## Checklist
- [x] Migrações Flyway versionadas
- [x] Evita N+1 nas rotas críticas
- [x] Índices coerentes
- [x] Transações no lugar certo
- [x] Exclusão lógica (Soft Delete) implementada
