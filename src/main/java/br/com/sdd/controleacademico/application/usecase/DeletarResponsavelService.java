package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.DeletarResponsavelUseCase;
import br.com.sdd.controleacademico.application.port.out.ResponsavelRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;

import java.util.UUID;

public class DeletarResponsavelService implements DeletarResponsavelUseCase {

    private final ResponsavelRepositoryPort repository;

    public DeletarResponsavelService(ResponsavelRepositoryPort repository) {
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
