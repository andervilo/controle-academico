package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.ListarAnosLetivosUseCase;
import br.com.sdd.controleacademico.application.port.out.AnoLetivoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import java.util.List;

public class ListarAnosLetivosService implements ListarAnosLetivosUseCase {
    private final AnoLetivoRepositoryPort repository;

    public ListarAnosLetivosService(AnoLetivoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<AnoLetivo> listarTodos() {
        return repository.listarTodos();
    }
}
