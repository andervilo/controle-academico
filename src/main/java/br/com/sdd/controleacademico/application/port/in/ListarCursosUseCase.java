package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Curso;
import java.util.List;

public interface ListarCursosUseCase {
    List<Curso> listarTodos();
}
