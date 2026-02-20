package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.DeletarDisciplinaUseCase;
import br.com.sdd.controleacademico.application.port.out.DisciplinaRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import java.util.UUID;

public class DeletarDisciplinaService implements DeletarDisciplinaUseCase {
    private final DisciplinaRepositoryPort repository;

    public DeletarDisciplinaService(DisciplinaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void deletar(UUID id) {
        if (repository.buscarPorId(id).isEmpty())
            throw new RegraDeNegocioException("Disciplina não encontrada");
        repository.deletar(id);
    }
}
