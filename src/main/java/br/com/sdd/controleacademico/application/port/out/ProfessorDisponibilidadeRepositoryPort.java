package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.ProfessorDisponibilidade;

import java.util.List;
import java.util.UUID;

public interface ProfessorDisponibilidadeRepositoryPort {
    ProfessorDisponibilidade salvar(ProfessorDisponibilidade disponibilidade);

    void deletar(UUID id);

    List<ProfessorDisponibilidade> listarPorProfessor(UUID professorId);
}
