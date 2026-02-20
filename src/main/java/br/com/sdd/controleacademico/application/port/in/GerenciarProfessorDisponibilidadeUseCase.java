package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.DiaSemana;
import br.com.sdd.controleacademico.domain.model.ProfessorDisponibilidade;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface GerenciarProfessorDisponibilidadeUseCase {
    ProfessorDisponibilidade adicionar(UUID professorId, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim);

    void remover(UUID disponibilidadeId);

    List<ProfessorDisponibilidade> listarPorProfessor(UUID professorId);
}
