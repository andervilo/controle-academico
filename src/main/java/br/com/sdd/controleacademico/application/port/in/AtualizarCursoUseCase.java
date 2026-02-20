package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Curso;
import java.util.UUID;

public interface AtualizarCursoUseCase {
    Curso atualizar(UUID id, String nome, String descricao);
}
