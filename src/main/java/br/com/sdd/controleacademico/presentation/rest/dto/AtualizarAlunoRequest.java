package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public record AtualizarAlunoRequest(
                @NotBlank(message = "Nome é obrigatório") String nome,

                @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,

                String telefone,

                @NotNull(message = "Data de nascimento é obrigatória") @Past(message = "Data de nascimento deve ser no passado") LocalDate dataNascimento,

                @NotNull(message = "ID do responsável financeiro é obrigatório") UUID responsavelId) {
}
