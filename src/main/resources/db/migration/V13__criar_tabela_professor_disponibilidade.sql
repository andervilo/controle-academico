CREATE TABLE professor_disponibilidade (
    id UUID PRIMARY KEY,
    professor_id UUID NOT NULL REFERENCES professor(id),
    dia_semana VARCHAR(20) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    UNIQUE (professor_id, dia_semana, hora_inicio)
);
