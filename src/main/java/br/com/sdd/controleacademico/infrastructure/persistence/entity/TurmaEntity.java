package br.com.sdd.controleacademico.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "turma")
@SQLDelete(sql = "UPDATE turma SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurmaEntity {
    @Id
    private UUID id;
    private String nome;

    @Column(name = "curso_id")
    private UUID cursoId;

    @Column(name = "ano_letivo_id")
    private UUID anoLetivoId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", insertable = false, updatable = false)
    private CursoEntity curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ano_letivo_id", insertable = false, updatable = false)
    private AnoLetivoEntity anoLetivo;
}
