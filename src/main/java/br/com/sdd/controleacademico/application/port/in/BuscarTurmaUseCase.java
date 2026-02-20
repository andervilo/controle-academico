package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Turma;
import java.util.Optional;
import java.util.UUID;

public interface BuscarTurmaUseCase {
    Optional<Turma> buscarPorId(UUID id);
}
