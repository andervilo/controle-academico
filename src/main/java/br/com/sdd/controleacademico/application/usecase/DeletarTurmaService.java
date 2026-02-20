package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.DeletarTurmaUseCase;
import br.com.sdd.controleacademico.application.port.out.TurmaRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import java.util.UUID;

public class DeletarTurmaService implements DeletarTurmaUseCase {
    private final TurmaRepositoryPort repository;

    public DeletarTurmaService(TurmaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void deletar(UUID id) {
        if (repository.buscarPorId(id).isEmpty())
            throw new RegraDeNegocioException("Turma não encontrada");
        repository.deletar(id);
    }
}
