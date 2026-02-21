package br.com.sdd.controleacademico.presentation.rest.dto;

import br.com.sdd.controleacademico.domain.model.CargoAdministrativo;
import java.util.UUID;

public record FuncionarioAdministrativoResponse(
        UUID id,
        String nome,
        String cpf,
        String email,
        CargoAdministrativo cargo,
        boolean ativo) {
}
