package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import java.time.LocalDate;

public interface CriarAnoLetivoUseCase {
    AnoLetivo criar(int ano, String descricao, LocalDate dataInicio, LocalDate dataFim);
}
