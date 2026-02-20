package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.GerenciarProfessorDisponibilidadeUseCase;
import br.com.sdd.controleacademico.application.port.out.ProfessorDisponibilidadeRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.ProfessorRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.DiaSemana;
import br.com.sdd.controleacademico.domain.model.ProfessorDisponibilidade;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class GerenciarProfessorDisponibilidadeService implements GerenciarProfessorDisponibilidadeUseCase {

    private final ProfessorDisponibilidadeRepositoryPort disponibilidadeRepository;
    private final ProfessorRepositoryPort professorRepository;

    public GerenciarProfessorDisponibilidadeService(ProfessorDisponibilidadeRepositoryPort disponibilidadeRepository,
            ProfessorRepositoryPort professorRepository) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public ProfessorDisponibilidade adicionar(UUID professorId, DiaSemana diaSemana,
            LocalTime horaInicio, LocalTime horaFim) {
        professorRepository.buscarPorId(professorId)
                .orElseThrow(() -> new RegraDeNegocioException("Professor não encontrado"));

        // Validar sobreposição com disponibilidades existentes
        List<ProfessorDisponibilidade> existentes = disponibilidadeRepository.listarPorProfessor(professorId);
        for (ProfessorDisponibilidade existente : existentes) {
            if (existente.getDiaSemana() == diaSemana
                    && existente.getHoraInicio().isBefore(horaFim)
                    && horaInicio.isBefore(existente.getHoraFim())) {
                throw new RegraDeNegocioException(
                        "Já existe disponibilidade cadastrada que se sobrepõe nesse horário: "
                                + existente.getDiaSemana() + " " + existente.getHoraInicio() + "-"
                                + existente.getHoraFim());
            }
        }

        var disponibilidade = ProfessorDisponibilidade.criar(professorId, diaSemana, horaInicio, horaFim);
        return disponibilidadeRepository.salvar(disponibilidade);
    }

    @Override
    public void remover(UUID disponibilidadeId) {
        disponibilidadeRepository.deletar(disponibilidadeId);
    }

    @Override
    public List<ProfessorDisponibilidade> listarPorProfessor(UUID professorId) {
        return disponibilidadeRepository.listarPorProfessor(professorId);
    }
}
