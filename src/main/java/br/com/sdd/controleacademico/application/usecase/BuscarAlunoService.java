package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.BuscarAlunoUseCase;
import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Aluno;

import java.util.Optional;
import java.util.UUID;

public class BuscarAlunoService implements BuscarAlunoUseCase {

    private final AlunoRepositoryPort repository;

    public BuscarAlunoService(AlunoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Aluno> buscarPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
