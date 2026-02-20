package br.com.sdd.controleacademico.presentation.rest.dto;

import java.time.LocalTime;
import java.util.UUID;

public record TurmaDisciplinaProfessorResponse(
                UUID id,
                UUID turmaId,
                String turmaNome,
                UUID disciplinaId,
                String disciplinaNome,
                UUID professorId,
                String professorNome,
                UUID anoLetivoId,
                String diaSemana,
                LocalTime horaInicio,
                LocalTime horaFim) {
}
