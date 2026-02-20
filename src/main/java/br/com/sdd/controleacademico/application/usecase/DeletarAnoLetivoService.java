package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.DeletarAnoLetivoUseCase;
import br.com.sdd.controleacademico.application.port.out.AnoLetivoRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import java.util.UUID;

public class DeletarAnoLetivoService implements DeletarAnoLetivoUseCase {
    private final AnoLetivoRepositoryPort repository;

    public DeletarAnoLetivoService(AnoLetivoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void deletar(UUID id) {
        if (repository.buscarPorId(id).isEmpty())
            throw new RegraDeNegocioException("Ano Letivo não encontrado");
        repository.deletar(id);
    }
}
