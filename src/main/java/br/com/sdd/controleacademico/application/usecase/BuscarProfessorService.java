package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.BuscarProfessorUseCase;
import br.com.sdd.controleacademico.application.port.out.ProfessorRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Professor;

import java.util.Optional;
import java.util.UUID;

public class BuscarProfessorService implements BuscarProfessorUseCase {
    private final ProfessorRepositoryPort repository;

    public BuscarProfessorService(ProfessorRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Professor> buscarPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
