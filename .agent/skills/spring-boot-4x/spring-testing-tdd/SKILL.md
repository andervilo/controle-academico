---
name: "spring-testing-tdd"
description: "Define estratégia e templates de testes (TDD), unitários, integração com Testcontainers e padrões de nomes/fixtures."
---

# Testes — TDD + Pirâmide + Testcontainers

## Estratégia
- Regra de ouro: testar regras de negócio e contratos; não testar o framework
- Pirâmide:
  - Unit (domínio e use cases)
  - Slice (WebMvcTest/DataJpaTest) quando útil
  - Integration (SpringBootTest + Testcontainers) para fluxos críticos

## TDD loop
1. Escrever teste falhando (Given/When/Then)
2. Implementar o mínimo para passar
3. Refatorar com segurança

## Padrões
- Nomes: `should_<expected>_when_<condition>`
- Arrange / Act / Assert
- Fixtures: builders para DTOs e entidades
- Teste de erro: validação, not found, conflict, forbidden

## Integração
- Testcontainers para Postgres/Redis/Rabbit/Kafka conforme stack
- Evitar testes flaky: usar awaitility só quando necessário
- Dados de seed via Flyway/Liquibase ou scripts de teste

## Checklist
- [ ] Unit tests cobrindo invariantes
- [ ] Integration tests cobrindo rotas críticas
- [ ] Testes de segurança (401/403)
- [ ] Testes de validação (400) e regras (409)
