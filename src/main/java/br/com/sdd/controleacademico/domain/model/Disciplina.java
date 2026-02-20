package br.com.sdd.controleacademico.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Disciplina {
    private UUID id;
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Disciplina(UUID id, String nome, String codigo, int cargaHoraria,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public static Disciplina criar(String nome, String codigo, int cargaHoraria) {
        return new Disciplina(UUID.randomUUID(), nome, codigo, cargaHoraria, LocalDateTime.now(), null);
    }

    public void atualizar(String nome, int cargaHoraria) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.updatedAt = LocalDateTime.now();
        validate();
    }

    private void validate() {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (codigo == null || codigo.isBlank())
            throw new IllegalArgumentException("Código é obrigatório");
        if (cargaHoraria <= 0)
            throw new IllegalArgumentException("Carga horária deve ser positiva");
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
