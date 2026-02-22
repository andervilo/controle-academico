package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.ListarCursosUseCase;
import br.com.sdd.controleacademico.application.port.out.CursoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Curso;
import java.util.List;

public class ListarCursosService implements ListarCursosUseCase {
    private final CursoRepositoryPort repository;

    public ListarCursosService(CursoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Curso> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    public br.com.sdd.controleacademico.domain.model.PaginationResult<Curso> listarPaginado(int page, int size) {
        return repository.listarPaginado(page, size);
    }
}
