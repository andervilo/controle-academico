package br.com.sdd.controleacademico.presentation.rest.dto;

import java.util.UUID;

public record CursoResponse(UUID id, String nome, String codigo, String descricao) {
}
