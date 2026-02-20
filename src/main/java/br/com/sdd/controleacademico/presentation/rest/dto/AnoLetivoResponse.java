package br.com.sdd.controleacademico.presentation.rest.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AnoLetivoResponse(UUID id, int ano, String descricao, LocalDate dataInicio, LocalDate dataFim,
        boolean ativo) {
}
