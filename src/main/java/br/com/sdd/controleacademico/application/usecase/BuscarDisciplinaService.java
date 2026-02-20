package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.BuscarDisciplinaUseCase;
import br.com.sdd.controleacademico.application.port.out.DisciplinaRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Disciplina;
import java.util.Optional;
import java.util.UUID;

public class BuscarDisciplinaService implements BuscarDisciplinaUseCase {
    private final DisciplinaRepositoryPort repository;

    public BuscarDisciplinaService(DisciplinaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Disciplina> buscarPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
