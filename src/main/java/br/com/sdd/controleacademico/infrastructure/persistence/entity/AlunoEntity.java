package br.com.sdd.controleacademico.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "aluno")
@SQLDelete(sql = "UPDATE aluno SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoEntity {
    @Id
    private UUID id;
    private String nome;
    private String matricula;
    private String cpf;
    private String email;
    private LocalDate dataNascimento;

    @Column(name = "responsavel_financeiro_id")
    private UUID responsavelFinanceiroId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_financeiro_id", insertable = false, updatable = false)
    private ResponsavelFinanceiroEntity responsavelFinanceiro;
}
