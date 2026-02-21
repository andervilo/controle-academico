package br.com.sdd.controleacademico.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class AlunoResponsavel {

    private UUID id;
    private UUID alunoId;
    private UUID responsavelId;
    private ParentescoEnum parentesco;
    private boolean permiteBuscarEscola;
    private boolean contatoEmergencia;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AlunoResponsavel(UUID id, UUID alunoId, UUID responsavelId, ParentescoEnum parentesco,
            boolean permiteBuscarEscola, boolean contatoEmergencia, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.alunoId = alunoId;
        this.responsavelId = responsavelId;
        this.parentesco = parentesco;
        this.permiteBuscarEscola = permiteBuscarEscola;
        this.contatoEmergencia = contatoEmergencia;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AlunoResponsavel criar(UUID alunoId, UUID responsavelId, ParentescoEnum parentesco,
            boolean permiteBuscarEscola, boolean contatoEmergencia) {
        return new AlunoResponsavel(UUID.randomUUID(), alunoId, responsavelId, parentesco, permiteBuscarEscola,
                contatoEmergencia, LocalDateTime.now(), null);
    }

    public void atualizar(ParentescoEnum parentesco, boolean permiteBuscarEscola, boolean contatoEmergencia) {
        this.parentesco = parentesco;
        this.permiteBuscarEscola = permiteBuscarEscola;
        this.contatoEmergencia = contatoEmergencia;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getAlunoId() {
        return alunoId;
    }

    public UUID getResponsavelId() {
        return responsavelId;
    }

    public ParentescoEnum getParentesco() {
        return parentesco;
    }

    public boolean isPermiteBuscarEscola() {
        return permiteBuscarEscola;
    }

    public boolean isContatoEmergencia() {
        return contatoEmergencia;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
