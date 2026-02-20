package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Disciplina;
import java.util.List;
import java.util.UUID;

public interface GerenciarProfessorDisciplinaUseCase {
    void adicionarProfessor(UUID disciplinaId, UUID professorId);

    void removerProfessor(UUID disciplinaId, UUID professorId);

    List<Disciplina> listarPorProfessor(UUID professorId);
}
