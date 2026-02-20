package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.ListarAlunosUseCase;
import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Aluno;

import java.util.List;

public class ListarAlunosService implements ListarAlunosUseCase {

    private final AlunoRepositoryPort repository;

    public ListarAlunosService(AlunoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Aluno> listarTodos() {
        return repository.listarTodos();
    }
}
