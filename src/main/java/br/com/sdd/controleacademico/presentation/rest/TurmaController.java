package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.domain.model.DiaSemana;
import br.com.sdd.controleacademico.domain.model.Turma;
import br.com.sdd.controleacademico.domain.model.TurmaDisciplinaProfessor;
import br.com.sdd.controleacademico.presentation.rest.dto.AtribuirProfessorDisciplinaRequest;
import br.com.sdd.controleacademico.presentation.rest.dto.TurmaDisciplinaProfessorResponse;
import br.com.sdd.controleacademico.presentation.rest.dto.TurmaRequest;
import br.com.sdd.controleacademico.presentation.rest.dto.TurmaResponse;
import br.com.sdd.controleacademico.presentation.rest.dto.MatricularAlunosRequest;
import br.com.sdd.controleacademico.presentation.rest.dto.AlunoResponse;
import br.com.sdd.controleacademico.domain.model.Aluno;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/turmas")
@Tag(name = "Turmas", description = "Gerenciamento de turmas")
public class TurmaController {

    private final CriarTurmaUseCase criarUseCase;
    private final BuscarTurmaUseCase buscarUseCase;
    private final AtualizarTurmaUseCase atualizarUseCase;
    private final DeletarTurmaUseCase deletarUseCase;
    private final ListarTurmasUseCase listarUseCase;
    private final GerenciarTurmaAlunoUseCase gerenciarAlunoUseCase;
    private final GerenciarTurmaDisciplinaProfessorUseCase gerenciarTDPUseCase;
    private final BuscarDisciplinaUseCase buscarDisciplinaUseCase;
    private final BuscarProfessorUseCase buscarProfessorUseCase;
    private final ListarAlunosUseCase listarAlunosUseCase;

    public TurmaController(CriarTurmaUseCase criarUseCase,
            BuscarTurmaUseCase buscarUseCase,
            AtualizarTurmaUseCase atualizarUseCase,
            DeletarTurmaUseCase deletarUseCase,
            ListarTurmasUseCase listarUseCase,
            GerenciarTurmaAlunoUseCase gerenciarAlunoUseCase,
            GerenciarTurmaDisciplinaProfessorUseCase gerenciarTDPUseCase,
            BuscarDisciplinaUseCase buscarDisciplinaUseCase,
            BuscarProfessorUseCase buscarProfessorUseCase,
            ListarAlunosUseCase listarAlunosUseCase) {
        this.criarUseCase = criarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.deletarUseCase = deletarUseCase;
        this.listarUseCase = listarUseCase;
        this.gerenciarAlunoUseCase = gerenciarAlunoUseCase;
        this.gerenciarTDPUseCase = gerenciarTDPUseCase;
        this.buscarDisciplinaUseCase = buscarDisciplinaUseCase;
        this.buscarProfessorUseCase = buscarProfessorUseCase;
        this.listarAlunosUseCase = listarAlunosUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar turma")
    public ResponseEntity<TurmaResponse> criar(@Valid @RequestBody TurmaRequest request) {
        Turma t = criarUseCase.criar(request.nome(), request.cursoId(), request.anoLetivoId());
        return ResponseEntity.created(URI.create("/api/v1/turmas/" + t.getId())).body(toResponse(t));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar turma por ID")
    public ResponseEntity<TurmaResponse> buscarPorId(@PathVariable UUID id) {
        return buscarUseCase.buscarPorId(id).map(t -> ResponseEntity.ok(toResponse(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todas as turmas")
    public ResponseEntity<List<TurmaResponse>> listarTodas() {
        return ResponseEntity.ok(listarUseCase.listarTodas().stream().map(this::toResponse).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar turma")
    public ResponseEntity<TurmaResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody TurmaRequest request) {
        return ResponseEntity.ok(
                toResponse(atualizarUseCase.atualizar(id, request.nome(), request.cursoId(), request.anoLetivoId())));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar turma")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ── Alunos ──────────────────────────────────────────────

    @PostMapping("/{turmaId}/alunos/{alunoId}")
    @Operation(summary = "Adicionar aluno à turma")
    public ResponseEntity<Void> adicionarAluno(@PathVariable UUID turmaId, @PathVariable UUID alunoId) {
        gerenciarAlunoUseCase.adicionarAluno(turmaId, alunoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{turmaId}/alunos/lote")
    @Operation(summary = "Matricular múltiplos alunos à turma")
    public ResponseEntity<Void> matricularAlunosLote(@PathVariable UUID turmaId,
            @Valid @RequestBody MatricularAlunosRequest request) {
        gerenciarAlunoUseCase.adicionarAlunos(turmaId, request.alunoIds());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{turmaId}/alunos/{alunoId}")
    @Operation(summary = "Remover aluno da turma")
    public ResponseEntity<Void> removerAluno(@PathVariable UUID turmaId, @PathVariable UUID alunoId) {
        gerenciarAlunoUseCase.removerAluno(turmaId, alunoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{turmaId}/alunos")
    @Operation(summary = "Listar alunos matriculados na turma")
    public ResponseEntity<List<AlunoResponse>> listarAlunosDaTurma(@PathVariable UUID turmaId) {
        return ResponseEntity.ok(listarAlunosUseCase.listarPorTurma(turmaId).stream()
                .map(this::toAlunoResponse).toList());
    }

    @GetMapping("/{turmaId}/alunos-disponiveis")
    @Operation(summary = "Listar alunos disponíveis para matrícula na turma")
    public ResponseEntity<List<AlunoResponse>> listarAlunosDisponiveis(@PathVariable UUID turmaId) {
        return ResponseEntity.ok(listarAlunosUseCase.listarDisponiveisParaTurma(turmaId).stream()
                .map(this::toAlunoResponse).toList());
    }

    // ── Grade Horária (Disciplinas ↔ Professores) ───────────

    @PostMapping("/{turmaId}/disciplinas/{disciplinaId}/professor/{professorId}")
    @Operation(summary = "Atribuir professor a uma disciplina na turma com horário")
    public ResponseEntity<TurmaDisciplinaProfessorResponse> atribuirProfessor(
            @PathVariable UUID turmaId,
            @PathVariable UUID disciplinaId,
            @PathVariable UUID professorId,
            @Valid @RequestBody AtribuirProfessorDisciplinaRequest request) {
        TurmaDisciplinaProfessor tdp = gerenciarTDPUseCase.atribuirProfessor(
                turmaId, disciplinaId, professorId,
                DiaSemana.valueOf(request.diaSemana()), request.horaInicio(), request.horaFim());
        return ResponseEntity.created(URI.create("/api/v1/turmas/" + turmaId + "/disciplinas-professores"))
                .body(toTDPResponse(tdp));
    }

    @DeleteMapping("/atribuicoes/{atribuicaoId}")
    @Operation(summary = "Remover atribuição de professor/disciplina")
    public ResponseEntity<Void> removerAtribuicao(@PathVariable UUID atribuicaoId) {
        gerenciarTDPUseCase.removerAtribuicao(atribuicaoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{turmaId}/disciplinas-professores")
    @Operation(summary = "Listar grade horária da turma (disciplinas, professores e horários)")
    public ResponseEntity<List<TurmaDisciplinaProfessorResponse>> listarGradeHoraria(@PathVariable UUID turmaId) {
        return ResponseEntity.ok(gerenciarTDPUseCase.listarPorTurma(turmaId).stream()
                .map(this::toTDPResponse).toList());
    }

    private TurmaResponse toResponse(Turma t) {
        return new TurmaResponse(t.getId(), t.getNome(), t.getCursoId(), t.getAnoLetivoId());
    }

    private TurmaDisciplinaProfessorResponse toTDPResponse(TurmaDisciplinaProfessor tdp) {
        String turmaNome = buscarUseCase.buscarPorId(tdp.getTurmaId())
                .map(t -> t.getNome()).orElse("N/A");
        String disciplinaNome = buscarDisciplinaUseCase.buscarPorId(tdp.getDisciplinaId())
                .map(d -> d.getNome()).orElse("N/A");
        String professorNome = buscarProfessorUseCase.buscarPorId(tdp.getProfessorId())
                .map(p -> p.getNome()).orElse("N/A");

        return new TurmaDisciplinaProfessorResponse(tdp.getId(), tdp.getTurmaId(), turmaNome, tdp.getDisciplinaId(),
                disciplinaNome, tdp.getProfessorId(), professorNome, tdp.getAnoLetivoId(), tdp.getDiaSemana().name(),
                tdp.getHoraInicio(), tdp.getHoraFim());
    }

    private AlunoResponse toAlunoResponse(Aluno a) {
        return new AlunoResponse(a.getId(), a.getNome(), a.getMatricula(), a.getCpf(), a.getEmail(), a.getTelefone(),
                a.getDataNascimento(), a.getResponsavelId());
    }
}
