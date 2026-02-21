package br.com.sdd.controleacademico.presentation.rest.dto;

import br.com.sdd.controleacademico.domain.model.ParentescoEnum;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record VincularResponsavelAlunoRequest(
        @NotNull(message = "O ID do respons\u00e1vel \u00e9 obrigat\u00f3rio") UUID responsavelId,
        @NotNull(message = "O parentesco \u00e9 obrigat\u00f3rio") ParentescoEnum parentesco,
        boolean permiteBuscarEscola,
        boolean contatoEmergencia) {
}
