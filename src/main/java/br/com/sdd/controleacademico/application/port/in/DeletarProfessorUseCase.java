package br.com.sdd.controleacademico.application.port.in;

import java.util.UUID;

public interface DeletarProfessorUseCase {
    void deletar(UUID id);
}
