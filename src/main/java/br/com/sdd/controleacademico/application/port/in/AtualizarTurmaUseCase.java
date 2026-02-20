package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Turma;
import java.util.UUID;

public interface AtualizarTurmaUseCase {
    Turma atualizar(UUID id, String nome, UUID cursoId, UUID anoLetivoId);
}
