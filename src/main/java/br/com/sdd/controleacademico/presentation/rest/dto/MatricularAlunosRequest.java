package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

public record MatricularAlunosRequest(
        @NotEmpty(message = "A lista de alunos não pode estar vazia") List<UUID> alunoIds) {
}
