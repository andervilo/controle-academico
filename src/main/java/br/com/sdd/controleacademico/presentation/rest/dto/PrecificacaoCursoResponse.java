package br.com.sdd.controleacademico.presentation.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PrecificacaoCursoResponse(
        UUID id,
        UUID cursoId,
        String cursoNome,
        UUID anoLetivoId,
        BigDecimal valorMatricula,
        BigDecimal valorMensalidade,
        int quantidadeMeses,
        int diaVencimentoPadrao) {
}
