package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.AtualizarResponsavelUseCase;
import br.com.sdd.controleacademico.application.port.out.ResponsavelRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Responsavel;

import java.util.UUID;

public class AtualizarResponsavelService implements AtualizarResponsavelUseCase {

    private final ResponsavelRepositoryPort repository;

    public AtualizarResponsavelService(ResponsavelRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Responsavel atualizar(UUID id, String nome, String email, String telefone) {
        Responsavel responsavel = repository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Responsável Financeiro não encontrado"));

        responsavel.atualizar(nome, email, telefone);

        return repository.atualizar(responsavel);
    }
}
