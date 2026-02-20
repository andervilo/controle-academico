package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record TurmaRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotNull(message = "Curso é obrigatório") UUID cursoId,
        @NotNull(message = "Ano Letivo é obrigatório") UUID anoLetivoId) {
}
