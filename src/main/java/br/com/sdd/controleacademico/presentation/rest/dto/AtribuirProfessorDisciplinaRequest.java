package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record AtribuirProfessorDisciplinaRequest(
        @NotNull(message = "Dia da semana é obrigatório") String diaSemana,
        @NotNull(message = "Hora de início é obrigatória") LocalTime horaInicio,
        @NotNull(message = "Hora de fim é obrigatória") LocalTime horaFim) {
}
