ALTER TABLE responsavel_financeiro ADD COLUMN deleted BOOLEAN DEFAULT FALSE;
ALTER TABLE aluno ADD COLUMN deleted BOOLEAN DEFAULT FALSE;

-- Remover UNIQUE constraint de CPF/Matricula para permitir soft delete sem conflito
-- (Considerando o trade-off de reuso de CPF: se soft-deleted, pode recadastrar?
-- Geralmente sim, ou filtra-se no unique index. 
-- Para Postgres 15+, usar NULLS NOT DISTINCT não ajuda aqui se for true.
-- Melhor criar partial unique index.)

ALTER TABLE responsavel_financeiro DROP CONSTRAINT responsavel_financeiro_cpf_key;
CREATE UNIQUE INDEX idx_responsavel_cpf_unique ON responsavel_financeiro(cpf) WHERE deleted = false;

ALTER TABLE aluno DROP CONSTRAINT aluno_matricula_key;
CREATE UNIQUE INDEX idx_aluno_matricula_unique ON aluno(matricula) WHERE deleted = false;

ALTER TABLE aluno DROP CONSTRAINT aluno_cpf_key;
CREATE UNIQUE INDEX idx_aluno_cpf_unique ON aluno(cpf) WHERE deleted = false;
