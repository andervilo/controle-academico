package br.com.sdd.controleacademico.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Professor {
    private UUID id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Professor(UUID id, String nome, String cpf, String email, String telefone,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public static Professor criar(String nome, String cpf, String email, String telefone) {
        return new Professor(UUID.randomUUID(), nome, cpf, email, telefone, LocalDateTime.now(), null);
    }

    public void atualizar(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.updatedAt = LocalDateTime.now();
        validate();
    }

    private void validate() {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (cpf == null || cpf.isBlank())
            throw new IllegalArgumentException("CPF é obrigatório");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email é obrigatório");
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
