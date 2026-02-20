package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.GerenciarTurmaDisciplinaProfessorUseCase;
import br.com.sdd.controleacademico.application.port.out.*;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class GerenciarTurmaDisciplinaProfessorService implements GerenciarTurmaDisciplinaProfessorUseCase {

    private final TurmaDisciplinaProfessorRepositoryPort tdpRepository;
    private final TurmaRepositoryPort turmaRepository;
    private final DisciplinaRepositoryPort disciplinaRepository;
    private final ProfessorRepositoryPort professorRepository;
    private final CursoRepositoryPort cursoRepository;
    private final ProfessorDisponibilidadeRepositoryPort disponibilidadeRepository;

    public GerenciarTurmaDisciplinaProfessorService(
            TurmaDisciplinaProfessorRepositoryPort tdpRepository,
            TurmaRepositoryPort turmaRepository,
            DisciplinaRepositoryPort disciplinaRepository,
            ProfessorRepositoryPort professorRepository,
            CursoRepositoryPort cursoRepository,
            ProfessorDisponibilidadeRepositoryPort disponibilidadeRepository) {
        this.tdpRepository = tdpRepository;
        this.turmaRepository = turmaRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
        this.cursoRepository = cursoRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
    }

    @Override
    public TurmaDisciplinaProfessor atribuirProfessor(UUID turmaId, UUID disciplinaId, UUID professorId,
            DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim) {
        // 1. Valida existência das entidades
        Turma turma = turmaRepository.buscarPorId(turmaId)
                .orElseThrow(() -> new RegraDeNegocioException("Turma não encontrada"));
        disciplinaRepository.buscarPorId(disciplinaId)
                .orElseThrow(() -> new RegraDeNegocioException("Disciplina não encontrada"));
        professorRepository.buscarPorId(professorId)
                .orElseThrow(() -> new RegraDeNegocioException("Professor não encontrado"));

        // 2. Valida que a disciplina pertence ao curso da turma
        List<UUID> disciplinasDoCurso = cursoRepository.listarDisciplinaIds(turma.getCursoId());
        if (!disciplinasDoCurso.contains(disciplinaId)) {
            throw new RegraDeNegocioException("Disciplina não pertence ao curso desta turma");
        }

        // 3. Valida que o professor pode ministrar esta disciplina
        List<UUID> professoresHabilitados = disciplinaRepository.listarProfessorIds(disciplinaId);
        if (!professoresHabilitados.contains(professorId)) {
            throw new RegraDeNegocioException("Professor não está habilitado para ministrar esta disciplina");
        }

        // 4. Valida que o professor está disponível nesse dia/horário
        List<ProfessorDisponibilidade> disponibilidades = disponibilidadeRepository.listarPorProfessor(professorId);
        boolean professorDisponivel = disponibilidades.stream()
                .anyMatch(d -> d.cobreHorario(diaSemana, horaInicio, horaFim));
        if (!professorDisponivel) {
            throw new RegraDeNegocioException(
                    "Professor não está disponível em " + diaSemana + " das " + horaInicio + " às " + horaFim);
        }

        // 5. Valida que o professor não tem conflito de horário com outra turma
        List<TurmaDisciplinaProfessor> aulasDoProfessor = tdpRepository.listarPorProfessorEAnoLetivo(professorId,
                turma.getAnoLetivoId());
        for (TurmaDisciplinaProfessor aula : aulasDoProfessor) {
            if (aula.conflitaCom(diaSemana, horaInicio, horaFim)) {
                throw new RegraDeNegocioException(
                        "Professor já possui aula em " + aula.getDiaSemana()
                                + " das " + aula.getHoraInicio() + " às " + aula.getHoraFim()
                                + " (Turma: " + aula.getTurmaId() + ")");
            }
        }

        // 6. Valida que a turma não tem conflito de horário com outra disciplina
        List<TurmaDisciplinaProfessor> aulasDaTurma = tdpRepository.listarPorTurma(turmaId);
        for (TurmaDisciplinaProfessor aula : aulasDaTurma) {
            if (aula.conflitaCom(diaSemana, horaInicio, horaFim)) {
                throw new RegraDeNegocioException(
                        "Turma já possui aula em " + aula.getDiaSemana()
                                + " das " + aula.getHoraInicio() + " às " + aula.getHoraFim()
                                + " (Disciplina: " + aula.getDisciplinaId() + ")");
            }
        }

        // 7. Tudo validado — cria a atribuição
        var atribuicao = TurmaDisciplinaProfessor.criar(
                turmaId, disciplinaId, professorId, turma.getAnoLetivoId(), diaSemana, horaInicio, horaFim);
        return tdpRepository.salvar(atribuicao);
    }

    @Override
    public void removerAtribuicao(UUID atribuicaoId) {
        tdpRepository.deletar(atribuicaoId);
    }

    @Override
    public List<TurmaDisciplinaProfessor> listarPorTurma(UUID turmaId) {
        return tdpRepository.listarPorTurma(turmaId);
    }

    @Override
    public List<TurmaDisciplinaProfessor> listarPorProfessor(UUID professorId) {
        return tdpRepository.listarPorProfessor(professorId);
    }
}
