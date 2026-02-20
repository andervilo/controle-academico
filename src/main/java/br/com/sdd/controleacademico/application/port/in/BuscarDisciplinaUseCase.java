package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Disciplina;
import java.util.Optional;
import java.util.UUID;

public interface BuscarDisciplinaUseCase {
    Optional<Disciplina> buscarPorId(UUID id);
}
