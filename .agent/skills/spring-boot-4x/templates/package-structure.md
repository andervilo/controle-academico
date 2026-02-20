# Package Structure Template (Clean/Hexagonal)

- domain: entidades/VOs, invariantes, domain services, eventos, exceções
- application: use cases e ports (in/out)
- presentation: controllers + DTOs + mappers + advice
- infrastructure: persistence + http clients + messaging + config

Regra: domain não depende de Spring.
