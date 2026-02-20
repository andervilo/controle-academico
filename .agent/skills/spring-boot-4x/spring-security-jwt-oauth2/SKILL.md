---
name: "spring-security-jwt-oauth2"
description: "Configura segurança por padrão (OAuth2 Resource Server + JWT), autorização, CORS, práticas de hardening e proteção de dados."
---

# Segurança — OAuth2 Resource Server + JWT

## Padrões
- Default deny: tudo autenticado por padrão; libere explicitamente só o necessário
- JWT com claims claros (roles/scopes)
- Autorizações: `@PreAuthorize` em controllers ou use cases

## Configurações essenciais
- CORS restritivo (origens explícitas, métodos necessários)
- CSRF: avaliar conforme tipo de cliente (SPA/Browser vs API)
- Headers de segurança (quando aplicável via gateway/infra)
- Senhas/tokens nunca em logs

## Autorização
- Padronizar: `SCOPE_xxx` ou `ROLE_xxx` (escolher 1)
- Mapear claims do JWT para authorities de forma consistente
- Regras por endpoint e por operação (ex.: READ/WRITE)

## Boas práticas
- Rate limit e brute-force geralmente no gateway
- Auditar ações sensíveis (logar “quem fez o quê” sem dados sensíveis)
- Validação forte de entrada para evitar mass assignment

## Checklist
- [ ] Authn/authz aplicadas
- [ ] CORS restritivo em prod
- [ ] Segredos fora do repo
- [ ] Logs sem tokens/PII sensível
