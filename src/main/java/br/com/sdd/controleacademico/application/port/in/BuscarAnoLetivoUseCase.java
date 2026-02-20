package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import java.util.Optional;
import java.util.UUID;

public interface BuscarAnoLetivoUseCase {
    Optional<AnoLetivo> buscarPorId(UUID id);
}
