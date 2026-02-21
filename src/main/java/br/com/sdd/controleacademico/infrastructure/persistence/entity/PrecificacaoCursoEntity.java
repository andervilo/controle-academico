package br.com.sdd.controleacademico.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "config_valor_curso")
@Getter
@Setter
@NoArgsConstructor
public class PrecificacaoCursoEntity {
    @Id
    private UUID id;

    @Column(name = "curso_id", nullable = false)
    private UUID cursoId;

    @Column(name = "ano_letivo_id", nullable = false)
    private UUID anoLetivoId;

    @Column(name = "valor_matricula", nullable = false)
    private BigDecimal valorMatricula;

    @Column(name = "valor_mensalidade", nullable = false)
    private BigDecimal valorMensalidade;

    @Column(name = "quantidade_meses", nullable = false)
    private int quantidadeMeses;

    @Column(name = "dia_vencimento_padrao")
    private int diaVencimentoPadrao;
}
