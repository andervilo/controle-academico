package br.com.sdd.controleacademico.presentation.rest.dto;

import java.util.UUID;

public record TurmaResponse(UUID id, String nome, UUID cursoId, UUID anoLetivoId) {
}
