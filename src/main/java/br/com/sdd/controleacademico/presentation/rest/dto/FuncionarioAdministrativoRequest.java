package br.com.sdd.controleacademico.presentation.rest.dto;

import br.com.sdd.controleacademico.domain.model.CargoAdministrativo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FuncionarioAdministrativoRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        String cpf,
        String email,
        @NotNull(message = "Cargo é obrigatório") CargoAdministrativo cargo,
        boolean ativo) {
}
