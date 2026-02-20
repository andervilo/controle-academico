package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record CursoRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Código é obrigatório") String codigo,
        String descricao) {
}
