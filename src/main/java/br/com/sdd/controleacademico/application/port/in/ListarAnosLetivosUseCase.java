package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import java.util.List;

public interface ListarAnosLetivosUseCase {
    List<AnoLetivo> listarTodos();
}
