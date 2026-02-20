package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.DeletarResponsavelFinanceiroUseCase;
import br.com.sdd.controleacademico.application.port.out.ResponsavelFinanceiroRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;

import java.util.UUID;

public class DeletarResponsavelFinanceiroService implements DeletarResponsavelFinanceiroUseCase {

    private final ResponsavelFinanceiroRepositoryPort repository;

    public DeletarResponsavelFinanceiroService(ResponsavelFinanceiroRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void deletar(UUID id) {
        if (repository.buscarPorId(id).isEmpty()) {
            throw new RegraDeNegocioException("Responsável Financeiro não encontrado");
        }
        repository.deletar(id);
    }
}
