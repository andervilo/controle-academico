-- Renomear tabela responsavel_financeiro para responsavel
ALTER TABLE responsavel_financeiro RENAME TO responsavel;

-- Renomear a coluna de foreign key no aluno
ALTER TABLE aluno RENAME COLUMN responsavel_financeiro_id TO responsavel_id;

-- Atualizar o nome da sequence associada, caso aplicável (PostgreSQL geralmente lida com o ID UUID direto mas por semântica)
-- (Não temos sequence de id porque usamos UUID gerenciado pela aplicação)

-- Criar tabela associativa para relacionamento N:N entre aluno e responsavel
CREATE TABLE aluno_responsavel (
    id UUID PRIMARY KEY,
    aluno_id UUID NOT NULL REFERENCES aluno(id),
    responsavel_id UUID NOT NULL REFERENCES responsavel(id),
    parentesco VARCHAR(50) NOT NULL,
    permite_buscar_escola BOOLEAN NOT NULL DEFAULT false,
    contato_emergencia BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    UNIQUE(aluno_id, responsavel_id)
);

-- Migrar o responsável principal (agora renomeado para responsavel_id) para a nova tabela associativa também
INSERT INTO aluno_responsavel (id, aluno_id, responsavel_id, parentesco, permite_buscar_escola, contato_emergencia)
SELECT gen_random_uuid(), id, responsavel_id, 'PAI', true, true FROM aluno;
