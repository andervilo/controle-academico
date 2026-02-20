CREATE TABLE turma (
    id UUID PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    curso_id UUID NOT NULL REFERENCES curso(id),
    ano_letivo_id UUID NOT NULL REFERENCES ano_letivo(id),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);
