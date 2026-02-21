package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.CriarResponsavelUseCase;
import br.com.sdd.controleacademico.application.port.out.ResponsavelRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Responsavel;

public class CriarResponsavelService implements CriarResponsavelUseCase {

    private final ResponsavelRepositoryPort repository;

    public CriarResponsavelService(ResponsavelRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Responsavel criar(String nome, String cpf, String email, String telefone) {
        if (repository.existePorCpf(cpf)) {
            throw new RegraDeNegocioException("CPF já cadastrado");
        }

        var responsavel = Responsavel.criar(nome, cpf, email, telefone);
        return repository.salvar(responsavel);
    }
}
