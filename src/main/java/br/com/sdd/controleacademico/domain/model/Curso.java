package br.com.sdd.controleacademico.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Curso {
    private UUID id;
    private String nome;
    private String codigo;
    private String descricao;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Curso(UUID id, String nome, String codigo, String descricao,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.descricao = descricao;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public static Curso criar(String nome, String codigo, String descricao) {
        return new Curso(UUID.randomUUID(), nome, codigo, descricao, LocalDateTime.now(), null);
    }

    public void atualizar(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.updatedAt = LocalDateTime.now();
        validate();
    }

    private void validate() {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (codigo == null || codigo.isBlank())
            throw new IllegalArgumentException("Código é obrigatório");
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

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
