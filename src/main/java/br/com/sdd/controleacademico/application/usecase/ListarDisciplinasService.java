package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.ListarDisciplinasUseCase;
import br.com.sdd.controleacademico.application.port.out.DisciplinaRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Disciplina;
import java.util.List;

public class ListarDisciplinasService implements ListarDisciplinasUseCase {
    private final DisciplinaRepositoryPort repository;

    public ListarDisciplinasService(DisciplinaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Disciplina> listarTodas() {
        return repository.listarTodos();
    }

    @Override
    public br.com.sdd.controleacademico.domain.model.PaginationResult<Disciplina> listarPaginado(int page, int size) {
        return repository.listarPaginado(page, size);
    }
}
