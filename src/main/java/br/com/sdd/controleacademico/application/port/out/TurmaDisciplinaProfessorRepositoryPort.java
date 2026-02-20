package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.TurmaDisciplinaProfessor;

import java.util.List;
import java.util.UUID;

public interface TurmaDisciplinaProfessorRepositoryPort {
    TurmaDisciplinaProfessor salvar(TurmaDisciplinaProfessor atribuicao);

    void deletar(UUID id);

    List<TurmaDisciplinaProfessor> listarPorTurma(UUID turmaId);

    List<TurmaDisciplinaProfessor> listarPorProfessor(UUID professorId);

    List<TurmaDisciplinaProfessor> listarPorProfessorEAnoLetivo(UUID professorId, UUID anoLetivoId);

    List<TurmaDisciplinaProfessor> listarPorAnoLetivo(UUID anoLetivoId);
}
