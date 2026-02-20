package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Aluno;
import java.util.Optional;
import java.util.UUID;

public interface BuscarAlunoUseCase {
    Optional<Aluno> buscarPorId(UUID id);
}
