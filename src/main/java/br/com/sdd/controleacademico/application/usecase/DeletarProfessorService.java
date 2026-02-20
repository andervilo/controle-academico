package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.DeletarProfessorUseCase;
import br.com.sdd.controleacademico.application.port.out.ProfessorRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;

import java.util.UUID;

public class DeletarProfessorService implements DeletarProfessorUseCase {
    private final ProfessorRepositoryPort repository;

    public DeletarProfessorService(ProfessorRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void deletar(UUID id) {
        if (repository.buscarPorId(id).isEmpty())
            throw new RegraDeNegocioException("Professor não encontrado");
        repository.deletar(id);
    }
}
