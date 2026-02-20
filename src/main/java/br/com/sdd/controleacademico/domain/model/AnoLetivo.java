package br.com.sdd.controleacademico.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AnoLetivo {
    private UUID id;
    private int ano;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AnoLetivo(UUID id, int ano, String descricao, LocalDate dataInicio, LocalDate dataFim,
            boolean ativo, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ano = ano;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativo = ativo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public static AnoLetivo criar(int ano, String descricao, LocalDate dataInicio, LocalDate dataFim) {
        return new AnoLetivo(UUID.randomUUID(), ano, descricao, dataInicio, dataFim, true, LocalDateTime.now(), null);
    }

    public void atualizar(int ano, String descricao, LocalDate dataInicio, LocalDate dataFim, boolean ativo) {
        this.ano = ano;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativo = ativo;
        this.updatedAt = LocalDateTime.now();
        validate();
    }

    private void validate() {
        if (ano <= 0)
            throw new IllegalArgumentException("Ano deve ser positivo");
        if (descricao == null || descricao.isBlank())
            throw new IllegalArgumentException("Descrição é obrigatória");
        if (dataInicio == null)
            throw new IllegalArgumentException("Data de início é obrigatória");
        if (dataFim == null)
            throw new IllegalArgumentException("Data de fim é obrigatória");
        if (dataFim.isBefore(dataInicio))
            throw new IllegalArgumentException("Data fim deve ser após data início");
    }

    public UUID getId() {
        return id;
    }

    public int getAno() {
        return ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
