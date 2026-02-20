---
name: "spring-architecture-clean-ddd"
description: "Define arquitetura (Clean/Hexagonal ou camadas) e estrutura de pacotes, com regras de dependência e padrões DDD quando aplicável."
---

# Arquitetura — Clean/Hexagonal + DDD (quando fizer sentido)

## Decisão rápida
- Use **Camadas** quando: CRUD simples, poucas regras, baixa chance de evolução complexa.
- Use **Clean/Hexagonal** quando: muitas regras, múltiplas integrações, necessidade de testes e evolução segura.

## Estrutura recomendada (Clean/Hexagonal)
```
src/main/java/com/acme/app
  domain/
    model/           (Entidades, VOs, invariantes)
    service/         (Domain services)
    event/           (Eventos de domínio)
    exception/       (Exceções de negócio)
  application/
    usecase/         (Casos de uso)
    port/
      in/            (Interfaces de entrada)
      out/           (Interfaces de saída)
  infrastructure/
    persistence/     (JPA, mappings, adapters)
    http/            (clients)
    messaging/       (kafka/rabbit)
    config/          (config Spring)
  presentation/
    rest/            (controllers, req/resp DTOs)
    advice/          (exception handlers)
```

## Regras de dependência
- domain: sem Spring, sem frameworks
- application: sem Spring, depende de domain
- infrastructure: dependem de application (e Spring)
- presentation: dependem de application, infrastructure (e Spring)

## DDD prático
- Entidade: identidade + invariantes
- VO: imutável, validação na criação
- Agregado: consistência transacional; expor métodos que preservem invariantes
- Domain events: para efeitos colaterais e integrações (com cuidado)

## Padrões de implementação
- Controllers finos: validar/autorizar/mapear → chamar use case
- Use case: orquestra transação, chama portas de saída
- Repositórios: interfaces em `application.port.out`, implementação em `infrastructure.persistence`
- Mappers: MapStruct entre DTO ↔ domain e domain ↔ JPA

## Checklist
- [ ] DTOs nunca são entidades JPA
- [ ] `domain` sem anotações Spring
- [ ] `application` sem anotações Spring (beans registrados via `@Configuration` na infrastructure)
- [ ] Exceções de negócio tipadas
- [ ] `@Transactional` apenas no nível de infrastructure config (não nos use cases)
