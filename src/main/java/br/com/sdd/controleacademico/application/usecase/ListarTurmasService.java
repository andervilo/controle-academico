package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.ListarTurmasUseCase;
import br.com.sdd.controleacademico.application.port.out.TurmaRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Turma;
import java.util.List;

public class ListarTurmasService implements ListarTurmasUseCase {
    private final TurmaRepositoryPort repository;

    public ListarTurmasService(TurmaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Turma> listarTodas() {
        return repository.listarTodos();
    }

    @Override
    public br.com.sdd.controleacademico.domain.model.PaginationResult<Turma> listarPaginado(int page, int size) {
        return repository.listarPaginado(page, size);
    }
}
