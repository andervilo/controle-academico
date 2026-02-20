package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Disciplina;
import java.util.UUID;

public interface AtualizarDisciplinaUseCase {
    Disciplina atualizar(UUID id, String nome, int cargaHoraria);
}
