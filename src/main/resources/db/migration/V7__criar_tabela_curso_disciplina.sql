CREATE TABLE curso_disciplina (
    curso_id UUID NOT NULL REFERENCES curso(id),
    disciplina_id UUID NOT NULL REFERENCES disciplina(id),
    PRIMARY KEY (curso_id, disciplina_id)
);
