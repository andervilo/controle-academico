CREATE TABLE turma_disciplina_professor (
    id UUID PRIMARY KEY,
    turma_id UUID NOT NULL REFERENCES turma(id),
    disciplina_id UUID NOT NULL REFERENCES disciplina(id),
    professor_id UUID NOT NULL REFERENCES professor(id),
    ano_letivo_id UUID NOT NULL REFERENCES ano_letivo(id),
    dia_semana VARCHAR(20) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    UNIQUE (turma_id, dia_semana, hora_inicio),
    UNIQUE (professor_id, ano_letivo_id, dia_semana, hora_inicio)
);
