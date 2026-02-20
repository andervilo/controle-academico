package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import java.time.LocalDate;
import java.util.UUID;

public interface AtualizarAnoLetivoUseCase {
    AnoLetivo atualizar(UUID id, int ano, String descricao, LocalDate dataInicio, LocalDate dataFim, boolean ativo);
}
