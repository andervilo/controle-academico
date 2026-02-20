package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;
import java.util.List;

public interface ListarResponsaveisFinanceirosUseCase {
    List<ResponsavelFinanceiro> listarTodos();
}
