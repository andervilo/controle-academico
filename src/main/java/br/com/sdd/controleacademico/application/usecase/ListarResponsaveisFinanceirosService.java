package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.ListarResponsaveisFinanceirosUseCase;
import br.com.sdd.controleacademico.application.port.out.ResponsavelFinanceiroRepositoryPort;
import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;

import java.util.List;

public class ListarResponsaveisFinanceirosService implements ListarResponsaveisFinanceirosUseCase {

    private final ResponsavelFinanceiroRepositoryPort repository;

    public ListarResponsaveisFinanceirosService(ResponsavelFinanceiroRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<ResponsavelFinanceiro> listarTodos() {
        return repository.listarTodos();
    }
}
