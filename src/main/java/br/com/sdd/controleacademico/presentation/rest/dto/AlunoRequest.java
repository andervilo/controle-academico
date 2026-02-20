package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.UUID;

public record AlunoRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,

        @CPF(message = "CPF inválido") String cpf, // CPF do aluno é opcional muitas vezes (menor de idade), mas se vier
                                                   // tem que ser válido

        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,

        @NotNull(message = "Data de nascimento é obrigatória") @Past(message = "Data de nascimento deve ser no passado") LocalDate dataNascimento,

        @NotNull(message = "ID do responsável financeiro é obrigatório") UUID responsavelFinanceiroId) {
}
