---
name: "spring-observability-actuator-otel"
description: "Configura observabilidade: Actuator, logs estruturados, correlation id, métricas Micrometer e tracing OpenTelemetry."
---

# Observabilidade — Actuator, Micrometer, OpenTelemetry

## Actuator
- Health: `/actuator/health`
- Metrics: `/actuator/metrics`
- Prometheus: `/actuator/prometheus` (se habilitado)

## Logs
- Estruturados (JSON em produção)
- Correlation ID:
  - Ler `X-Correlation-Id` se existir
  - Gerar se não existir
  - Propagar em chamadas externas
- Nunca logar PII sensível ou tokens

## Métricas
- Latência por endpoint
- Taxa de erro por status
- Pool/conexões DB
- Mensageria (lag/queue depth) se aplicável

## Tracing
- OpenTelemetry para traces distribuídos
- Instrumentar clients HTTP e mensageria
- Atributos úteis: `userId` apenas se não sensível e com cuidado (preferir subject hash)

## Checklist
- [ ] Actuator habilitado com endpoints necessários
- [ ] Correlation ID end-to-end
- [ ] Métricas essenciais disponíveis
- [ ] Tracing configurado para produção
