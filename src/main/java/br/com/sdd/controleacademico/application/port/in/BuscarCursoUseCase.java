package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Curso;
import java.util.Optional;
import java.util.UUID;

public interface BuscarCursoUseCase {
    Optional<Curso> buscarPorId(UUID id);
}
