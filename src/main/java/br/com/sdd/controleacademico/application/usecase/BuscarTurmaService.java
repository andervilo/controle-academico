package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.BuscarTurmaUseCase;
import br.com.sdd.controleacademico.application.port.out.TurmaRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Turma;
import java.util.Optional;
import java.util.UUID;

public class BuscarTurmaService implements BuscarTurmaUseCase {
    private final TurmaRepositoryPort repository;

    public BuscarTurmaService(TurmaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Turma> buscarPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
