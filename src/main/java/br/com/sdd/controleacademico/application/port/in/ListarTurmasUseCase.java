package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Turma;
import java.util.List;

public interface ListarTurmasUseCase {
    List<Turma> listarTodas();
}
