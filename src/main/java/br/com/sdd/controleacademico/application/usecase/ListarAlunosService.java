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

    @Override
    public br.com.sdd.controleacademico.domain.model.PaginationResult<Aluno> listarPaginado(int page, int size) {
        return repository.listarPaginado(page, size);
    }

    @Override
    public List<Aluno> listarPorTurma(java.util.UUID turmaId) {
        return repository.listarPorTurma(turmaId);
    }

    @Override
    public List<Aluno> listarDisponiveisParaTurma(java.util.UUID turmaId) {
        return repository.listarDisponiveisParaTurma(turmaId);
    }
}
