package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.BuscarResponsavelFinanceiroUseCase;
import br.com.sdd.controleacademico.application.port.out.ResponsavelFinanceiroRepositoryPort;
import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;

import java.util.Optional;
import java.util.UUID;

public class BuscarResponsavelFinanceiroService implements BuscarResponsavelFinanceiroUseCase {

    private final ResponsavelFinanceiroRepositoryPort repository;

    public BuscarResponsavelFinanceiroService(ResponsavelFinanceiroRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ResponsavelFinanceiro> buscarPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
