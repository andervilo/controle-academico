package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.DeletarCursoUseCase;
import br.com.sdd.controleacademico.application.port.out.CursoRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import java.util.UUID;

public class DeletarCursoService implements DeletarCursoUseCase {
    private final CursoRepositoryPort repository;

    public DeletarCursoService(CursoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void deletar(UUID id) {
        if (repository.buscarPorId(id).isEmpty())
            throw new RegraDeNegocioException("Curso não encontrado");
        repository.deletar(id);
    }
}
