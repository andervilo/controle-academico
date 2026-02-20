---
name: "spring-docker-deploy"
description: "Empacotamento e entrega: Dockerfile multi-stage, compose local, profiles, config por ambiente e checklist de operação."
---

# Docker & Deploy — Padrões de entrega

## Dockerfile
- Multi-stage build
- Imagem final minimalista
- Usuário não-root
- Expor porta configurável
- Healthcheck (ou via orchestrator)

## Config por ambiente
- `application.yml` + `application-dev.yml` + `application-prod.yml`
- Segredos via env vars / secret manager
- Fail fast em config inválida

## Compose local
- Postgres + app
- Volumes para dados
- Portas e envs documentadas

## Checklist
- [ ] Build reproduzível
- [ ] Imagem pequena e segura
- [ ] Compose sobe o stack local
- [ ] Profiles corretos (dev/prod)
