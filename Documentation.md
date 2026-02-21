# Documentação do Projeto: Controle Acadêmico SDD

## 1. Visão Geral
O **Controle Acadêmico SDD** é uma plataforma de gestão escolar robusta, projetada para gerenciar o ciclo de vida acadêmico completo, desde o cadastro de alunos e professores até a enturmação, grade horária e parametrização financeira anual. O projeto segue princípios de **Arquitetura Limpa (Clean Architecture)** e **Domain-Driven Design (DDD)** para garantir manutenibilidade e escalabilidade.

---

## 2. Objetivo
Centralizar a gestão administrativa e acadêmica de uma instituição de ensino, permitindo o controle de:
*   Corpo docente e discente.
*   Matriz curricular e cursos.
*   Planejamento anual (Anos Letivos e Turmas).
*   Grade horária (vínculo entre Turma, Disciplina e Professor).
*   Configurações institucionais e precificação de mensalidades por curso/ano.

---

## 3. Backend (Java)

### 3.1 Tecnologias
*   **Linguagem:** Java 21.
*   **Framework:** Spring Boot 3.3.x.
*   **Persistência:** Spring Data JPA / Hibernate.
*   **Banco de Dados:** PostgreSQL.
*   **Migração de Dados:** Flyway (Versionamento de schema).
*   **Documentação:** SpringDoc OpenAPI (Swagger).
*   **Utilitários:** Lombok (Boilerplate), MapStruct (Mapeamento de DTOs).

### 3.2 Arquitetura (Clean Architecture / DDD)
O projeto está dividido em camadas para separação de preocupações:

1.  **Domain (Núcleo):**
    *   `model`: Entidades de negócio (Aluno, Turma, Escola, etc.).
    *   `exception`: Exceções de domínio (RegraDeNegocioException).
    *   `port.out`: Interfaces de repositório (segundo o padrão Dependency Inversion).
2.  **Application (Lógica de Uso):**
    *   `port.in`: Interfaces que definem as operações permitidas (Use Cases).
    *   `usecase`: Implementação dos serviços de aplicação.
3.  **Infrastructure (Detalhes Técnicos):**
    *   `persistence.entity`: Entidades JPA para mapeamento ORM.
    *   `persistence.repository`: Interfaces Spring Data JpaRepository.
    *   `persistence.adapter`: Implementação dos `port.out`, fazendo a ponte entre domínio e banco de dados.
4.  **Presentation (Interface Externa):**
    *   `rest`: Controllers Spring REST.
    *   `rest.dto`: Objetos de transferência de dados (Request/Response Records).

---

## 4. Frontend (Angular)

### 4.1 Tecnologias
*   **Framework:** Angular 19.
*   **Design System:** PrimeNG (Baseado no template Sakai-NG).
*   **Estilização:** CSS Vanilla + PrimeFlex / Tailwind (utilitários de layout).
*   **Ícones:** PrimeIcons.
*   **Estado:** Signals (Angular 17+ core features para reatividade).

### 4.2 Estrutura de Pastas
*   `app/pages`: Componentes de página (AlunoListComponent, TurmaFormComponent, etc.).
*   `app/layout`: Componentes estruturais (Sidebar, Topbar, Layout principal).
*   `app/service`: Serviços de comunicação com a API (comunicação baseada em `HttpClient`).
*   `environments`: Configurações de API por ambiente.

---

## 5. Módulos e Funcionalidades Core

### 5.1 Alunos e Responsáveis
*   Geração automática de **Matrícula** no padrão `YYYY + Sequencial`.
*   Fix de Timezone para tratamento rigoroso de datas de nascimento.
*   Vínculo com Responsável Financeiro (CPF obrigatório para emissão de notas/contratos).

### 5.2 Turmas e Grade Horária
*   **Enturmação:** Matrícula de alunos em turmas específicas com controle de disponibilidade.
*   **TDP (Turma-Disciplina-Professor):** Gerenciamento da grade horária, definindo qual professor leciona qual disciplina, em qual dia e horário.

### 5.3 Configurações Administrativas
*   **Escola:** Cadastro singleton dos dados da instituição (CNPJ, INEP, Logotipo em Base64).
*   **Financeiro Anual:** Parametrização de valores de matrícula e mensalidade por curso e ano letivo, permitindo reajustes anuais sem perder o histórico.
*   **Equipe Gestora:** Cadastro de diretores e secretários para assinaturas em documentos.

---

## 6. Infraestrutura e Deploy

O projeto é 100% containerizado utilizando **Docker** e **Docker Compose**, composto por 3 serviços:
1.  **`db`**: PostgreSQL 16.
2.  **`app`**: Backend Spring Boot (exposto na porta 8080).
3.  **`frontend`**: Servidor Nginx servindo o build estático do Angular (exposto na porta 4200).

### Comandos Úteis
*   Subir ambiente completo: `docker-compose up -d --build`
*   Ver logs do app: `docker logs -f controle-academico-app-1`

---

## 7. Próximos Passos Sugeridos
*   Implementação de módulo de Notas e Frequência.
*   Geração de Boleto bancário (Integração com gateway).
*   Emissão de documentos PDF (Matrícula, Histórico).
*   Autenticação e Autorização (Spring Security + JWT).
