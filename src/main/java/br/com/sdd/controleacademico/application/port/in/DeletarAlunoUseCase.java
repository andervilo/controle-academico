package br.com.sdd.controleacademico.application.port.in;

import java.util.UUID;

public interface DeletarAlunoUseCase {
    void deletar(UUID id);
}
