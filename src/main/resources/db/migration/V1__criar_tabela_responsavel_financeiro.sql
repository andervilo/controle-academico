CREATE TABLE responsavel_financeiro (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX idx_responsavel_cpf ON responsavel_financeiro(cpf);
CREATE INDEX idx_responsavel_email ON responsavel_financeiro(email);
