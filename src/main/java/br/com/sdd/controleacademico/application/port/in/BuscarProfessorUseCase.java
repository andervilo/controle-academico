package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Professor;
import java.util.Optional;
import java.util.UUID;

public interface BuscarProfessorUseCase {
    Optional<Professor> buscarPorId(UUID id);
}
