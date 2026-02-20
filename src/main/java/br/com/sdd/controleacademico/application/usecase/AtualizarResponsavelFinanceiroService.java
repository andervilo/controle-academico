package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.AtualizarResponsavelFinanceiroUseCase;
import br.com.sdd.controleacademico.application.port.out.ResponsavelFinanceiroRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;

import java.util.UUID;

public class AtualizarResponsavelFinanceiroService implements AtualizarResponsavelFinanceiroUseCase {

    private final ResponsavelFinanceiroRepositoryPort repository;

    public AtualizarResponsavelFinanceiroService(ResponsavelFinanceiroRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public ResponsavelFinanceiro atualizar(UUID id, String nome, String email, String telefone) {
        ResponsavelFinanceiro responsavel = repository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Responsável Financeiro não encontrado"));

        responsavel.atualizar(nome, email, telefone);

        return repository.atualizar(responsavel);
    }
}
