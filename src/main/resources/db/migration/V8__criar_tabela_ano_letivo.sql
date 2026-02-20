CREATE TABLE ano_letivo (
    id UUID PRIMARY KEY,
    ano INTEGER NOT NULL,
    descricao VARCHAR(50) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE UNIQUE INDEX idx_ano_letivo_descricao_unique ON ano_letivo(descricao) WHERE deleted = false;
