package br.com.sdd.controleacademico.presentation.rest.dto;

import java.time.LocalTime;
import java.util.UUID;

public record TurmaDisciplinaProfessorResponse(
                UUID id,
                UUID turmaId,
                UUID disciplinaId,
                UUID professorId,
                UUID anoLetivoId,
                String diaSemana,
                LocalTime horaInicio,
                LocalTime horaFim) {
}
