package br.com.sdd.controleacademico.presentation.rest.dto;

import br.com.sdd.controleacademico.domain.model.ParentescoEnum;
import java.util.UUID;

public record AlunoResponsavelResponse(
        br.com.sdd.controleacademico.presentation.rest.dto.ResponsavelResponse responsavel,
        ParentescoEnum parentesco,
        boolean permiteBuscarEscola,
        boolean contatoEmergencia) {
}
