package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;
import java.util.Optional;
import java.util.UUID;

public interface BuscarResponsavelFinanceiroUseCase {
    Optional<ResponsavelFinanceiro> buscarPorId(UUID id);
}
