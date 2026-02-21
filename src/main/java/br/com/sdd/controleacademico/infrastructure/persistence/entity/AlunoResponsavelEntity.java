package br.com.sdd.controleacademico.infrastructure.persistence.entity;

import br.com.sdd.controleacademico.domain.model.ParentescoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aluno_responsavel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoResponsavelEntity {

    @Id
    private UUID id;

    @Column(name = "aluno_id", nullable = false)
    private UUID alunoId;

    @Column(name = "responsavel_id", nullable = false)
    private UUID responsavelId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParentescoEnum parentesco;

    @Column(name = "permite_buscar_escola", nullable = false)
    private boolean permiteBuscarEscola;

    @Column(name = "contato_emergencia", nullable = false)
    private boolean contatoEmergencia;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", insertable = false, updatable = false)
    private AlunoEntity aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id", insertable = false, updatable = false)
    private ResponsavelEntity responsavel;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
