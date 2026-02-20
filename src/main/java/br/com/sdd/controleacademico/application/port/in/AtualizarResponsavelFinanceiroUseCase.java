package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;
import java.util.UUID;

public interface AtualizarResponsavelFinanceiroUseCase {
    ResponsavelFinanceiro atualizar(UUID id, String nome, String email, String telefone);
}
