CREATE TABLE curso (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    codigo VARCHAR(20) NOT NULL,
    descricao TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE UNIQUE INDEX idx_curso_codigo_unique ON curso(codigo) WHERE deleted = false;
