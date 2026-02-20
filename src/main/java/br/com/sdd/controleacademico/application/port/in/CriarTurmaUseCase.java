package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Turma;
import java.util.UUID;

public interface CriarTurmaUseCase {
    Turma criar(String nome, UUID cursoId, UUID anoLetivoId);
}
