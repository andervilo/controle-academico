package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.BuscarCursoUseCase;
import br.com.sdd.controleacademico.application.port.out.CursoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Curso;
import java.util.Optional;
import java.util.UUID;

public class BuscarCursoService implements BuscarCursoUseCase {
    private final CursoRepositoryPort repository;

    public BuscarCursoService(CursoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Curso> buscarPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
