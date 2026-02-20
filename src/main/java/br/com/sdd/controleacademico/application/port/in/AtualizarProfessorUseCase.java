package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Professor;
import java.util.UUID;

public interface AtualizarProfessorUseCase {
    Professor atualizar(UUID id, String nome, String email, String telefone);
}
