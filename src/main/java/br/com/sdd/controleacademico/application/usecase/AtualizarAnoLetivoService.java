package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.AtualizarAnoLetivoUseCase;
import br.com.sdd.controleacademico.application.port.out.AnoLetivoRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import java.time.LocalDate;
import java.util.UUID;

public class AtualizarAnoLetivoService implements AtualizarAnoLetivoUseCase {
    private final AnoLetivoRepositoryPort repository;

    public AtualizarAnoLetivoService(AnoLetivoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public AnoLetivo atualizar(UUID id, int ano, String descricao, LocalDate dataInicio, LocalDate dataFim,
            boolean ativo) {
        AnoLetivo anoLetivo = repository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Ano Letivo não encontrado"));
        anoLetivo.atualizar(ano, descricao, dataInicio, dataFim, ativo);
        return repository.atualizar(anoLetivo);
    }
}
