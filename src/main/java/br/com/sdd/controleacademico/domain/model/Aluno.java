package br.com.sdd.controleacademico.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Aluno {
    private UUID id;
    private String nome;
    private String matricula;
    private String cpf;
    private String email;
    private LocalDate dataNascimento;
    private UUID responsavelFinanceiroId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtor auxiliar para atualização
    public void atualizar(String nome, String email, LocalDate dataNascimento, UUID responsavelFinanceiroId) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.responsavelFinanceiroId = responsavelFinanceiroId;
        this.updatedAt = LocalDateTime.now();
        validate();
    }

    public Aluno(UUID id, String nome, String matricula, String cpf, String email, LocalDate dataNascimento,
            UUID responsavelFinanceiroId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.responsavelFinanceiroId = responsavelFinanceiroId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public static Aluno criar(String nome, String matricula, String cpf, String email, LocalDate dataNascimento,
            UUID responsavelFinanceiroId) {
        return new Aluno(UUID.randomUUID(), nome, matricula, cpf, email, dataNascimento, responsavelFinanceiroId,
                LocalDateTime.now(), null);
    }

    private void validate() {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (matricula == null || matricula.isBlank())
            throw new IllegalArgumentException("Matrícula é obrigatória");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email é obrigatório");
        if (dataNascimento == null)
            throw new IllegalArgumentException("Data de nascimento é obrigatória");
        if (responsavelFinanceiroId == null)
            throw new IllegalArgumentException("Responsável Financeiro é obrigatório");
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public UUID getResponsavelFinanceiroId() {
        return responsavelFinanceiroId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
