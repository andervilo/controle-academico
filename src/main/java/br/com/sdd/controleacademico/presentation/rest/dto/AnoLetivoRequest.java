package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AnoLetivoRequest(
        int ano,
        @NotBlank(message = "Descrição é obrigatória") String descricao,
        @NotNull(message = "Data de início é obrigatória") LocalDate dataInicio,
        @NotNull(message = "Data de fim é obrigatória") LocalDate dataFim) {
}
