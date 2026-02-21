package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record PrecificacaoCursoRequest(
        @NotNull(message = "ID do curso é obrigatório") UUID cursoId,
        @NotNull(message = "ID do ano letivo é obrigatório") UUID anoLetivoId,
        @NotNull(message = "Valor da matrícula é obrigatório") BigDecimal valorMatricula,
        @NotNull(message = "Valor da mensalidade é obrigatório") BigDecimal valorMensalidade,
        int quantidadeMeses,
        int diaVencimentoPadrao) {
}
