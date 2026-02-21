package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.BuscarResponsavelUseCase;
import br.com.sdd.controleacademico.application.port.out.ResponsavelRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Responsavel;

import java.util.Optional;
import java.util.UUID;

public class BuscarResponsavelService implements BuscarResponsavelUseCase {

    private final ResponsavelRepositoryPort repository;

    public BuscarResponsavelService(ResponsavelRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Responsavel> buscarPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
