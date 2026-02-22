package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.domain.model.DiaSemana;
import br.com.sdd.controleacademico.domain.model.Disciplina;
import br.com.sdd.controleacademico.domain.model.Professor;
import br.com.sdd.controleacademico.domain.model.PaginationResult;
import br.com.sdd.controleacademico.domain.model.ProfessorDisponibilidade;
import br.com.sdd.controleacademico.domain.model.TurmaDisciplinaProfessor;
import br.com.sdd.controleacademico.presentation.rest.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/professores")
@Tag(name = "Professores", description = "Gerenciamento de professores")
public class ProfessorController {

    private final CriarProfessorUseCase criarUseCase;
    private final BuscarProfessorUseCase buscarUseCase;
    private final AtualizarProfessorUseCase atualizarUseCase;
    private final DeletarProfessorUseCase deletarUseCase;
    private final ListarProfessoresUseCase listarUseCase;
    private final GerenciarProfessorDisponibilidadeUseCase disponibilidadeUseCase;
    private final GerenciarTurmaDisciplinaProfessorUseCase gerenciarTDPUseCase;
    private final GerenciarProfessorDisciplinaUseCase gerenciarProfessorDisciplinaUseCase;
    private final BuscarDisciplinaUseCase buscarDisciplinaUseCase;
    private final BuscarTurmaUseCase buscarTurmaUseCase;

    public ProfessorController(CriarProfessorUseCase criarUseCase,
            BuscarProfessorUseCase buscarUseCase,
            AtualizarProfessorUseCase atualizarUseCase,
            DeletarProfessorUseCase deletarUseCase,
            ListarProfessoresUseCase listarUseCase,
            GerenciarProfessorDisponibilidadeUseCase disponibilidadeUseCase,
            GerenciarTurmaDisciplinaProfessorUseCase gerenciarTDPUseCase,
            GerenciarProfessorDisciplinaUseCase gerenciarProfessorDisciplinaUseCase,
            BuscarDisciplinaUseCase buscarDisciplinaUseCase,
            BuscarTurmaUseCase buscarTurmaUseCase) {
        this.criarUseCase = criarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.deletarUseCase = deletarUseCase;
        this.listarUseCase = listarUseCase;
        this.disponibilidadeUseCase = disponibilidadeUseCase;
        this.gerenciarTDPUseCase = gerenciarTDPUseCase;
        this.gerenciarProfessorDisciplinaUseCase = gerenciarProfessorDisciplinaUseCase;
        this.buscarDisciplinaUseCase = buscarDisciplinaUseCase;
        this.buscarTurmaUseCase = buscarTurmaUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar professor")
    public ResponseEntity<ProfessorResponse> criar(@Valid @RequestBody ProfessorRequest request) {
        Professor p = criarUseCase.criar(request.nome(), request.cpf(), request.email(), request.telefone());
        return ResponseEntity.created(URI.create("/api/v1/professores/" + p.getId())).body(toResponse(p));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar professor por ID")
    public ResponseEntity<ProfessorResponse> buscarPorId(@PathVariable UUID id) {
        return buscarUseCase.buscarPorId(id).map(p -> ResponseEntity.ok(toResponse(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar professores (paginado)")
    public ResponseEntity<PaginatedResponse<ProfessorResponse>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginationResult<Professor> result = listarUseCase.listarPaginado(page, size);

        List<ProfessorResponse> content = result.content().stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(new PaginatedResponse<>(
                content,
                result.totalElements(),
                result.totalPages(),
                result.size(),
                result.number()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar professor")
    public ResponseEntity<ProfessorResponse> atualizar(@PathVariable UUID id,
            @Valid @RequestBody ProfessorRequest request) {
        return ResponseEntity
                .ok(toResponse(atualizarUseCase.atualizar(id, request.nome(), request.email(), request.telefone())));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar professor")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ── Disponibilidade ─────────────────────────────────────

    @PostMapping("/{professorId}/disponibilidades")
    @Operation(summary = "Adicionar disponibilidade ao professor")
    public ResponseEntity<ProfessorDisponibilidadeResponse> adicionarDisponibilidade(
            @PathVariable UUID professorId,
            @Valid @RequestBody ProfessorDisponibilidadeRequest request) {
        ProfessorDisponibilidade d = disponibilidadeUseCase.adicionar(
                professorId, DiaSemana.valueOf(request.diaSemana()), request.horaInicio(), request.horaFim());
        return ResponseEntity.created(URI.create("/api/v1/professores/" + professorId + "/disponibilidades"))
                .body(toDisponibilidadeResponse(d));
    }

    @GetMapping("/{professorId}/disponibilidades")
    @Operation(summary = "Listar disponibilidades do professor")
    public ResponseEntity<List<ProfessorDisponibilidadeResponse>> listarDisponibilidades(
            @PathVariable UUID professorId) {
        return ResponseEntity.ok(disponibilidadeUseCase.listarPorProfessor(professorId).stream()
                .map(this::toDisponibilidadeResponse).toList());
    }

    @DeleteMapping("/disponibilidades/{disponibilidadeId}")
    @Operation(summary = "Remover disponibilidade")
    public ResponseEntity<Void> removerDisponibilidade(@PathVariable UUID disponibilidadeId) {
        disponibilidadeUseCase.remover(disponibilidadeId);
        return ResponseEntity.noContent().build();
    }

    // ── Turmas e Disciplinas ────────────────────────────────

    @GetMapping("/{professorId}/turmas-disciplinas")
    @Operation(summary = "Listar turmas e disciplinas que o professor ministra")
    public ResponseEntity<List<TurmaDisciplinaProfessorResponse>> listarTurmasDisciplinas(
            @PathVariable UUID professorId) {
        return ResponseEntity.ok(gerenciarTDPUseCase.listarPorProfessor(professorId).stream()
                .map(this::toTDPResponse).toList());
    }

    // ── Disciplinas Habilitadas ────────────────────────────
    @GetMapping("/{professorId}/disciplinas")
    @Operation(summary = "Listar disciplinas que o professor está habilitado a ministrar")
    public ResponseEntity<List<DisciplinaResponse>> listarDisciplinas(@PathVariable UUID professorId) {
        return ResponseEntity.ok(gerenciarProfessorDisciplinaUseCase.listarPorProfessor(professorId).stream()
                .map(this::toDisciplinaResponse).toList());
    }

    @PostMapping("/{professorId}/disciplinas/{disciplinaId}")
    @Operation(summary = "Habilitar professor em uma disciplina")
    public ResponseEntity<Void> habilitarDisciplina(@PathVariable UUID professorId, @PathVariable UUID disciplinaId) {
        gerenciarProfessorDisciplinaUseCase.adicionarProfessor(disciplinaId, professorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{professorId}/disciplinas/{disciplinaId}")
    @Operation(summary = "Desabilitar professor de uma disciplina")
    public ResponseEntity<Void> deshabilitarDisciplina(@PathVariable UUID professorId,
            @PathVariable UUID disciplinaId) {
        gerenciarProfessorDisciplinaUseCase.removerProfessor(disciplinaId, professorId);
        return ResponseEntity.noContent().build();
    }

    private DisciplinaResponse toDisciplinaResponse(Disciplina d) {
        return new DisciplinaResponse(d.getId(), d.getNome(), d.getCodigo(), d.getCargaHoraria());
    }

    private ProfessorResponse toResponse(Professor p) {
        return new ProfessorResponse(p.getId(), p.getNome(), p.getCpf(), p.getEmail(), p.getTelefone());
    }

    private ProfessorDisponibilidadeResponse toDisponibilidadeResponse(ProfessorDisponibilidade d) {
        return new ProfessorDisponibilidadeResponse(d.getId(), d.getProfessorId(),
                d.getDiaSemana().name(), d.getHoraInicio(), d.getHoraFim());
    }

    private TurmaDisciplinaProfessorResponse toTDPResponse(TurmaDisciplinaProfessor tdp) {
        String turmaNome = buscarTurmaUseCase.buscarPorId(tdp.getTurmaId())
                .map(t -> t.getNome()).orElse("N/A");
        String disciplinaNome = buscarDisciplinaUseCase.buscarPorId(tdp.getDisciplinaId())
                .map(d -> d.getNome()).orElse("N/A");
        String professorNome = buscarUseCase.buscarPorId(tdp.getProfessorId())
                .map(p -> p.getNome()).orElse("N/A");

        return new TurmaDisciplinaProfessorResponse(tdp.getId(), tdp.getTurmaId(), turmaNome, tdp.getDisciplinaId(),
                disciplinaNome, tdp.getProfessorId(), professorNome, tdp.getAnoLetivoId(), tdp.getDiaSemana().name(),
                tdp.getHoraInicio(), tdp.getHoraFim());
    }
}
