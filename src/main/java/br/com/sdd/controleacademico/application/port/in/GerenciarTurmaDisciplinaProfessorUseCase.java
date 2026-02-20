package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.DiaSemana;
import br.com.sdd.controleacademico.domain.model.TurmaDisciplinaProfessor;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface GerenciarTurmaDisciplinaProfessorUseCase {
    TurmaDisciplinaProfessor atribuirProfessor(UUID turmaId, UUID disciplinaId, UUID professorId,
            DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim);

    void removerAtribuicao(UUID atribuicaoId);

    List<TurmaDisciplinaProfessor> listarPorTurma(UUID turmaId);

    List<TurmaDisciplinaProfessor> listarPorProfessor(UUID professorId);
}
