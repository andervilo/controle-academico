# Controle Acadêmico - Spring Boot 3.x

Este projeto foi gerado seguindo as boas práticas da **skill spring-boot-4x**.

## Estrutura do Projeto

A arquitetura segue o padrão **Clean Architecture / Hexagonal**:

```
src/main/java/br/com/sdd/controleacademico
  domain/          # Núcleo da aplicação (Entidades, VOs, Domain Services)
  application/     # Casos de uso e portas (interfaces)
  infrastructure/  # Implementações técnicas (Persistência, HTTP, Config)
  presentation/    # REST Controllers e DTOs
```

## Setup Inicial e Execução

### Pré-requisitos
- Java 21+
- Docker & Docker Compose
- Maven (Opcional, wrapper incluído no Dockerfile)

### Rodar com Docker Compose (Recomendado)

Isso subirá o banco de dados PostgreSQL e a aplicação:

```bash
docker-compose up --build
```

A aplicação estará disponível em `http://localhost:8080`.
O banco de dados: `localhost:5432` (user: `postgres`, pass: `postgres`, db: `controle_academico`)

### Rodar Localmente

1. Suba apenas o banco de dados:
```bash
docker-compose up -d db
```

2. Execute a aplicação via Maven:
```bash
mvn spring-boot:run
```

## Próximos Passos

1. Definir o modelo de domínio em `domain/model`.
2. Criar migrations Flyway em `src/main/resources/db/migration` (ex: `V1__init.sql`).
3. Implementar casos de uso em `application/usecase`.
