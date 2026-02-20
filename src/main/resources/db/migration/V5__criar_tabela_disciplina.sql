CREATE TABLE disciplina (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    codigo VARCHAR(20) NOT NULL,
    carga_horaria INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE UNIQUE INDEX idx_disciplina_codigo_unique ON disciplina(codigo) WHERE deleted = false;
