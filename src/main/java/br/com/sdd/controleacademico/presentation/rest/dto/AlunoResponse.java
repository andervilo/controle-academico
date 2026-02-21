package br.com.sdd.controleacademico.presentation.rest.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AlunoResponse(
                UUID id,
                String nome,
                String matricula,
                String cpf,
                String email,
                String telefone,
                LocalDate dataNascimento,
                UUID responsavelId) {
}
