package br.com.sdd.controleacademico.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrecificacaoCurso {
    private UUID id;
    private UUID cursoId;
    private UUID anoLetivoId;
    private BigDecimal valorMatricula;
    private BigDecimal valorMensalidade;
    private int quantidadeMeses;
    private int diaVencimentoPadrao;

    public static PrecificacaoCurso criar(UUID cursoId, UUID anoLetivoId, BigDecimal valorMatricula,
            BigDecimal valorMensalidade, int quantidadeMeses, int diaVencimentoPadrao) {
        return new PrecificacaoCurso(UUID.randomUUID(), cursoId, anoLetivoId, valorMatricula, valorMensalidade,
                quantidadeMeses, diaVencimentoPadrao);
    }
}
