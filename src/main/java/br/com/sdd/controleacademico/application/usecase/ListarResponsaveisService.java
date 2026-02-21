package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.ListarResponsaveisUseCase;
import br.com.sdd.controleacademico.application.port.out.ResponsavelRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Responsavel;

import java.util.List;

public class ListarResponsaveisService implements ListarResponsaveisUseCase {

    private final ResponsavelRepositoryPort repository;

    public ListarResponsaveisService(ResponsavelRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Responsavel> listarTodos() {
        return repository.listarTodos();
    }
}
