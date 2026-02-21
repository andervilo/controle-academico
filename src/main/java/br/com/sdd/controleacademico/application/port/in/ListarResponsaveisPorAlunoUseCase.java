package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.AlunoResponsavelDetalhe;
import java.util.List;
import java.util.UUID;

public interface ListarResponsaveisPorAlunoUseCase {
    List<AlunoResponsavelDetalhe> listar(UUID alunoId);
}
