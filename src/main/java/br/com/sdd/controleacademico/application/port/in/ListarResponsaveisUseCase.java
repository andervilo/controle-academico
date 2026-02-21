package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Responsavel;
import java.util.List;

public interface ListarResponsaveisUseCase {
    List<Responsavel> listarTodos();
}
