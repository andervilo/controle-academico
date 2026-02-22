package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Disciplina;
import java.util.List;

public interface ListarDisciplinasUseCase {
    List<Disciplina> listarTodas();

    br.com.sdd.controleacademico.domain.model.PaginationResult<Disciplina> listarPaginado(int page, int size);
}
