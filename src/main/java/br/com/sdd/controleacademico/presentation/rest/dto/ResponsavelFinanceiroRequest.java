package br.com.sdd.controleacademico.presentation.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ResponsavelFinanceiroRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,

        @NotBlank(message = "CPF é obrigatório") @CPF(message = "CPF inválido") String cpf,

        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,

        String telefone) {
}
