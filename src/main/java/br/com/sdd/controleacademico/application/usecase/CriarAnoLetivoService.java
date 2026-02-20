package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.CriarAnoLetivoUseCase;
import br.com.sdd.controleacademico.application.port.out.AnoLetivoRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import java.time.LocalDate;

public class CriarAnoLetivoService implements CriarAnoLetivoUseCase {
    private final AnoLetivoRepositoryPort repository;

    public CriarAnoLetivoService(AnoLetivoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public AnoLetivo criar(int ano, String descricao, LocalDate dataInicio, LocalDate dataFim) {
        if (repository.existePorDescricao(descricao))
            throw new RegraDeNegocioException("Ano letivo já cadastrado");
        return repository.salvar(AnoLetivo.criar(ano, descricao, dataInicio, dataFim));
    }
}
