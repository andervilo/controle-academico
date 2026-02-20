package br.com.sdd.controleacademico.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Turma {
    private UUID id;
    private String nome;
    private UUID cursoId;
    private UUID anoLetivoId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Turma(UUID id, String nome, UUID cursoId, UUID anoLetivoId,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nome = nome;
        this.cursoId = cursoId;
        this.anoLetivoId = anoLetivoId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public static Turma criar(String nome, UUID cursoId, UUID anoLetivoId) {
        return new Turma(UUID.randomUUID(), nome, cursoId, anoLetivoId, LocalDateTime.now(), null);
    }

    public void atualizar(String nome, UUID cursoId, UUID anoLetivoId) {
        this.nome = nome;
        this.cursoId = cursoId;
        this.anoLetivoId = anoLetivoId;
        this.updatedAt = LocalDateTime.now();
        validate();
    }

    private void validate() {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (cursoId == null)
            throw new IllegalArgumentException("Curso é obrigatório");
        if (anoLetivoId == null)
            throw new IllegalArgumentException("Ano Letivo é obrigatório");
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public UUID getAnoLetivoId() {
        return anoLetivoId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
