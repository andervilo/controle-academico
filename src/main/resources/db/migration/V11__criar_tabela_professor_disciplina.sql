CREATE TABLE professor_disciplina (
    professor_id UUID NOT NULL REFERENCES professor(id),
    disciplina_id UUID NOT NULL REFERENCES disciplina(id),
    PRIMARY KEY (professor_id, disciplina_id)
);
