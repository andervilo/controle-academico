package br.com.sdd.controleacademico.presentation.rest.dto;

import java.time.LocalTime;
import java.util.UUID;

public record ProfessorDisponibilidadeResponse(
        UUID id,
        UUID professorId,
        String diaSemana,
        LocalTime horaInicio,
        LocalTime horaFim) {
}
