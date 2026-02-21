package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Aluno;
import java.util.List;

public interface ListarAlunosUseCase {
    List<Aluno> listarTodos();

    List<Aluno> listarPorTurma(java.util.UUID turmaId);

    List<Aluno> listarDisponiveisParaTurma(java.util.UUID turmaId);
}
