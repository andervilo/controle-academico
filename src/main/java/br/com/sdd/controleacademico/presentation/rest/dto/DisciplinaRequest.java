package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record DisciplinaRequest(
                @NotBlank(message = "Nome é obrigatório") String nome,
                @NotBlank(message = "Código é obrigatório") String codigo,
                @Min(value = 1, message = "Carga horária deve ser positiva") int cargaHoraria) {
}
