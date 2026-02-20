CREATE TABLE turma_aluno (
    turma_id UUID NOT NULL REFERENCES turma(id),
    aluno_id UUID NOT NULL REFERENCES aluno(id),
    PRIMARY KEY (turma_id, aluno_id)
);
