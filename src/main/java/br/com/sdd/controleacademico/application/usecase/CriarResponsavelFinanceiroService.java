package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.CriarResponsavelFinanceiroUseCase;
import br.com.sdd.controleacademico.application.port.out.ResponsavelFinanceiroRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;

public class CriarResponsavelFinanceiroService implements CriarResponsavelFinanceiroUseCase {

    private final ResponsavelFinanceiroRepositoryPort repository;

    public CriarResponsavelFinanceiroService(ResponsavelFinanceiroRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public ResponsavelFinanceiro criar(String nome, String cpf, String email, String telefone) {
        if (repository.existePorCpf(cpf)) {
            throw new RegraDeNegocioException("CPF já cadastrado");
        }

        var responsavel = ResponsavelFinanceiro.criar(nome, cpf, email, telefone);
        return repository.salvar(responsavel);
    }
}
