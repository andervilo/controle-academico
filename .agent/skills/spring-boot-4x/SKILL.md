---
name: "spring-boot-4x-master"
description: "Skill orquestradora: define o workflow e aciona sub-skills para construir apps Spring Boot 4.x com boas práticas de produção."
---

# Spring Boot 4.x — Master Orchestrator Skill

## Missão
Entregar aplicações Spring Boot 4.x prontas para produção com **arquitetura consistente, segurança por padrão, observabilidade e testes**, usando as sub-skills deste pacote.

## Como usar (sempre)
1. Leia o requisito / Spec.md e extraia:
   - Entidades/VOs, regras, fluxos e exceções
   - Contratos (endpoints/eventos), authz/authn e integrações
   - Requisitos não-funcionais (SLO, logs, auditoria, LGPD, etc.)
2. Escolha o caminho arquitetural:
   - CRUD simples → camadas, disciplina de DTO e validação
   - Domínio médio/complexo → Clean/Hexagonal (ports & adapters)
3. Execute na ordem (chame as sub-skills):
   1) **spring-architecture-clean-ddd**
   2) **spring-api-rest-design**
   3) **spring-persistence-jpa-flyway**
   4) **spring-security-jwt-oauth2**
   5) **spring-testing-tdd**
   6) **spring-observability-actuator-otel**
   7) **spring-docker-deploy**
4. Ao final, valide a Definition of Done.

## Definition of Done (DoD)
- [ ] OpenAPI atualizado + endpoints consistentes (HTTP, paginação, erros)
- [ ] DTOs separados de entidades JPA + validação na borda
- [ ] Regras de negócio no domínio/application; controllers finos
- [ ] Segurança aplicada (JWT/OAuth2), default deny, CORS restritivo
- [ ] Migrações Flyway versionadas + índices coerentes
- [ ] Observabilidade: Actuator, logs estruturados, correlation id, métricas, tracing
- [ ] Testes unit + integração (Testcontainers) para caminhos críticos
- [ ] Dockerfile/compose local funcional + config por ambiente

## Políticas do agente
- Preferir **simplicidade e clareza** a abstrações excessivas.
- Não expor entidades, tokens, segredos ou stacktrace ao cliente.
- Timeouts por padrão em integrações; retries só idempotente.
- Manter mudanças pequenas, testadas e revisáveis.

## Saídas esperadas quando solicitado
Quando o usuário pedir “crie X”:
- Estrutura de pacotes
- Controllers + DTOs + mappers
- Use cases/services + regras
- Adapters (persistence/integrations)
- Migrações Flyway
- Testes (unit + integration)
- Observabilidade e segurança aplicadas
- Docker/compose (se aplicável)
