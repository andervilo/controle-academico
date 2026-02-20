CREATE TABLE aluno (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    cpf VARCHAR(14) UNIQUE,
    email VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    responsavel_financeiro_id UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_aluno_responsavel FOREIGN KEY (responsavel_financeiro_id) REFERENCES responsavel_financeiro(id)
);

CREATE INDEX idx_aluno_matricula ON aluno(matricula);
CREATE INDEX idx_aluno_responsavel ON aluno(responsavel_financeiro_id);
