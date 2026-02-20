package br.com.sdd.controleacademico.presentation.rest.dto;

import java.util.UUID;

public record DisciplinaResponse(UUID id, String nome, String codigo, int cargaHoraria) {
}
