package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.BuscarAnoLetivoUseCase;
import br.com.sdd.controleacademico.application.port.out.AnoLetivoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import java.util.Optional;
import java.util.UUID;

public class BuscarAnoLetivoService implements BuscarAnoLetivoUseCase {
    private final AnoLetivoRepositoryPort repository;

    public BuscarAnoLetivoService(AnoLetivoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AnoLetivo> buscarPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
