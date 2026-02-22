package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Professor;
import br.com.sdd.controleacademico.domain.model.PaginationResult;
import java.util.List;

public interface ListarProfessoresUseCase {
    List<Professor> listarTodos();

    PaginationResult<Professor> listarPaginado(int page, int size);
}
