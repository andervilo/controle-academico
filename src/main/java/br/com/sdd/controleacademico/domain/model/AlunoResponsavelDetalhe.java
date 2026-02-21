package br.com.sdd.controleacademico.domain.model;

import br.com.sdd.controleacademico.domain.model.Responsavel;
import br.com.sdd.controleacademico.domain.model.ParentescoEnum;

public record AlunoResponsavelDetalhe(
        Responsavel responsavel,
        ParentescoEnum parentesco,
        boolean permiteBuscarEscola,
        boolean contatoEmergencia) {
}
