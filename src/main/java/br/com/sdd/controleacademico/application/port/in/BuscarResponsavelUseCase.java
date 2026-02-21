package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Responsavel;
import java.util.Optional;
import java.util.UUID;

public interface BuscarResponsavelUseCase {
    Optional<Responsavel> buscarPorId(UUID id);
}
