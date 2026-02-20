package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Professor;
import java.util.List;

public interface ListarProfessoresUseCase {
    List<Professor> listarTodos();
}
