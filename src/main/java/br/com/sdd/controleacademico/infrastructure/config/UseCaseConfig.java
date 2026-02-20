package br.com.sdd.controleacademico.infrastructure.config;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.application.port.out.*;
import br.com.sdd.controleacademico.application.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Configuração Spring que registra os Use Cases da camada application como
 * beans.
 * <p>
 * A camada application é pura Java (sem Spring). O wiring e o controle
 * transacional
 * ficam na camada infrastructure, respeitando as regras de dependência da Clean
 * Architecture.
 */
@Configuration
public class UseCaseConfig {

    // ─────────────────────────────────────────────────────────
    // Responsável Financeiro
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public CriarResponsavelFinanceiroUseCase criarResponsavelFinanceiroUseCase(
            ResponsavelFinanceiroRepositoryPort repository) {
        return new CriarResponsavelFinanceiroService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public BuscarResponsavelFinanceiroUseCase buscarResponsavelFinanceiroUseCase(
            ResponsavelFinanceiroRepositoryPort repository) {
        return new BuscarResponsavelFinanceiroService(repository);
    }

    @Bean
    @Transactional
    public AtualizarResponsavelFinanceiroUseCase atualizarResponsavelFinanceiroUseCase(
            ResponsavelFinanceiroRepositoryPort repository) {
        return new AtualizarResponsavelFinanceiroService(repository);
    }

    @Bean
    @Transactional
    public DeletarResponsavelFinanceiroUseCase deletarResponsavelFinanceiroUseCase(
            ResponsavelFinanceiroRepositoryPort repository) {
        return new DeletarResponsavelFinanceiroService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public ListarResponsaveisFinanceirosUseCase listarResponsaveisFinanceirosUseCase(
            ResponsavelFinanceiroRepositoryPort repository) {
        return new ListarResponsaveisFinanceirosService(repository);
    }

    // ─────────────────────────────────────────────────────────
    // Aluno
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public CriarAlunoUseCase criarAlunoUseCase(
            AlunoRepositoryPort alunoRepository,
            ResponsavelFinanceiroRepositoryPort responsavelRepository) {
        return new CriarAlunoService(alunoRepository, responsavelRepository);
    }

    @Bean
    @Transactional(readOnly = true)
    public BuscarAlunoUseCase buscarAlunoUseCase(AlunoRepositoryPort repository) {
        return new BuscarAlunoService(repository);
    }

    @Bean
    @Transactional
    public AtualizarAlunoUseCase atualizarAlunoUseCase(
            AlunoRepositoryPort alunoRepository,
            ResponsavelFinanceiroRepositoryPort responsavelRepository) {
        return new AtualizarAlunoService(alunoRepository, responsavelRepository);
    }

    @Bean
    @Transactional
    public DeletarAlunoUseCase deletarAlunoUseCase(AlunoRepositoryPort repository) {
        return new DeletarAlunoService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public ListarAlunosUseCase listarAlunosUseCase(AlunoRepositoryPort repository) {
        return new ListarAlunosService(repository);
    }

    // ─────────────────────────────────────────────────────────
    // Professor
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public CriarProfessorUseCase criarProfessorUseCase(ProfessorRepositoryPort repository) {
        return new CriarProfessorService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public BuscarProfessorUseCase buscarProfessorUseCase(ProfessorRepositoryPort repository) {
        return new BuscarProfessorService(repository);
    }

    @Bean
    @Transactional
    public AtualizarProfessorUseCase atualizarProfessorUseCase(ProfessorRepositoryPort repository) {
        return new AtualizarProfessorService(repository);
    }

    @Bean
    @Transactional
    public DeletarProfessorUseCase deletarProfessorUseCase(ProfessorRepositoryPort repository) {
        return new DeletarProfessorService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public ListarProfessoresUseCase listarProfessoresUseCase(ProfessorRepositoryPort repository) {
        return new ListarProfessoresService(repository);
    }

    // ─────────────────────────────────────────────────────────
    // Disciplina
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public CriarDisciplinaUseCase criarDisciplinaUseCase(DisciplinaRepositoryPort repository) {
        return new CriarDisciplinaService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public BuscarDisciplinaUseCase buscarDisciplinaUseCase(DisciplinaRepositoryPort repository) {
        return new BuscarDisciplinaService(repository);
    }

    @Bean
    @Transactional
    public AtualizarDisciplinaUseCase atualizarDisciplinaUseCase(DisciplinaRepositoryPort repository) {
        return new AtualizarDisciplinaService(repository);
    }

    @Bean
    @Transactional
    public DeletarDisciplinaUseCase deletarDisciplinaUseCase(DisciplinaRepositoryPort repository) {
        return new DeletarDisciplinaService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public ListarDisciplinasUseCase listarDisciplinasUseCase(DisciplinaRepositoryPort repository) {
        return new ListarDisciplinasService(repository);
    }

    @Bean
    @Transactional
    public GerenciarProfessorDisciplinaUseCase gerenciarProfessorDisciplinaUseCase(
            DisciplinaRepositoryPort disciplinaRepository,
            ProfessorRepositoryPort professorRepository) {
        return new GerenciarProfessorDisciplinaService(disciplinaRepository, professorRepository);
    }

    // ─────────────────────────────────────────────────────────
    // Curso
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public CriarCursoUseCase criarCursoUseCase(CursoRepositoryPort repository) {
        return new CriarCursoService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public BuscarCursoUseCase buscarCursoUseCase(CursoRepositoryPort repository) {
        return new BuscarCursoService(repository);
    }

    @Bean
    @Transactional
    public AtualizarCursoUseCase atualizarCursoUseCase(CursoRepositoryPort repository) {
        return new AtualizarCursoService(repository);
    }

    @Bean
    @Transactional
    public DeletarCursoUseCase deletarCursoUseCase(CursoRepositoryPort repository) {
        return new DeletarCursoService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public ListarCursosUseCase listarCursosUseCase(CursoRepositoryPort repository) {
        return new ListarCursosService(repository);
    }

    @Bean
    @Transactional
    public GerenciarCursoDisciplinaUseCase gerenciarCursoDisciplinaUseCase(
            CursoRepositoryPort cursoRepository,
            DisciplinaRepositoryPort disciplinaRepository) {
        return new GerenciarCursoDisciplinaService(cursoRepository, disciplinaRepository);
    }

    // ─────────────────────────────────────────────────────────
    // Ano Letivo
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public CriarAnoLetivoUseCase criarAnoLetivoUseCase(AnoLetivoRepositoryPort repository) {
        return new CriarAnoLetivoService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public BuscarAnoLetivoUseCase buscarAnoLetivoUseCase(AnoLetivoRepositoryPort repository) {
        return new BuscarAnoLetivoService(repository);
    }

    @Bean
    @Transactional
    public AtualizarAnoLetivoUseCase atualizarAnoLetivoUseCase(AnoLetivoRepositoryPort repository) {
        return new AtualizarAnoLetivoService(repository);
    }

    @Bean
    @Transactional
    public DeletarAnoLetivoUseCase deletarAnoLetivoUseCase(AnoLetivoRepositoryPort repository) {
        return new DeletarAnoLetivoService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public ListarAnosLetivosUseCase listarAnosLetivosUseCase(AnoLetivoRepositoryPort repository) {
        return new ListarAnosLetivosService(repository);
    }

    // ─────────────────────────────────────────────────────────
    // Turma
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public CriarTurmaUseCase criarTurmaUseCase(
            TurmaRepositoryPort turmaRepository,
            CursoRepositoryPort cursoRepository,
            AnoLetivoRepositoryPort anoLetivoRepository) {
        return new CriarTurmaService(turmaRepository, cursoRepository, anoLetivoRepository);
    }

    @Bean
    @Transactional(readOnly = true)
    public BuscarTurmaUseCase buscarTurmaUseCase(TurmaRepositoryPort repository) {
        return new BuscarTurmaService(repository);
    }

    @Bean
    @Transactional
    public AtualizarTurmaUseCase atualizarTurmaUseCase(
            TurmaRepositoryPort turmaRepository,
            CursoRepositoryPort cursoRepository,
            AnoLetivoRepositoryPort anoLetivoRepository) {
        return new AtualizarTurmaService(turmaRepository, cursoRepository, anoLetivoRepository);
    }

    @Bean
    @Transactional
    public DeletarTurmaUseCase deletarTurmaUseCase(TurmaRepositoryPort repository) {
        return new DeletarTurmaService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public ListarTurmasUseCase listarTurmasUseCase(TurmaRepositoryPort repository) {
        return new ListarTurmasService(repository);
    }

    @Bean
    @Transactional
    public GerenciarTurmaAlunoUseCase gerenciarTurmaAlunoUseCase(
            TurmaRepositoryPort turmaRepository,
            AlunoRepositoryPort alunoRepository) {
        return new GerenciarTurmaAlunoService(turmaRepository, alunoRepository);
    }

    // ─────────────────────────────────────────────────────────
    // Professor Disponibilidade
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public GerenciarProfessorDisponibilidadeUseCase gerenciarProfessorDisponibilidadeUseCase(
            ProfessorDisponibilidadeRepositoryPort disponibilidadeRepository,
            ProfessorRepositoryPort professorRepository) {
        return new GerenciarProfessorDisponibilidadeService(disponibilidadeRepository, professorRepository);
    }

    // ─────────────────────────────────────────────────────────
    // Turma ↔ Disciplina ↔ Professor (Grade Horária)
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public GerenciarTurmaDisciplinaProfessorUseCase gerenciarTurmaDisciplinaProfessorUseCase(
            TurmaDisciplinaProfessorRepositoryPort tdpRepository,
            TurmaRepositoryPort turmaRepository,
            DisciplinaRepositoryPort disciplinaRepository,
            ProfessorRepositoryPort professorRepository,
            CursoRepositoryPort cursoRepository,
            ProfessorDisponibilidadeRepositoryPort disponibilidadeRepository) {
        return new GerenciarTurmaDisciplinaProfessorService(
                tdpRepository, turmaRepository, disciplinaRepository,
                professorRepository, cursoRepository, disponibilidadeRepository);
    }
}
