package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.domain.model.DiaSemana;
import br.com.sdd.controleacademico.domain.model.Professor;
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

    public ProfessorController(CriarProfessorUseCase criarUseCase,
            BuscarProfessorUseCase buscarUseCase,
            AtualizarProfessorUseCase atualizarUseCase,
            DeletarProfessorUseCase deletarUseCase,
            ListarProfessoresUseCase listarUseCase,
            GerenciarProfessorDisponibilidadeUseCase disponibilidadeUseCase,
            GerenciarTurmaDisciplinaProfessorUseCase gerenciarTDPUseCase) {
        this.criarUseCase = criarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.deletarUseCase = deletarUseCase;
        this.listarUseCase = listarUseCase;
        this.disponibilidadeUseCase = disponibilidadeUseCase;
        this.gerenciarTDPUseCase = gerenciarTDPUseCase;
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
    @Operation(summary = "Listar todos os professores")
    public ResponseEntity<List<ProfessorResponse>> listarTodos() {
        return ResponseEntity.ok(listarUseCase.listarTodos().stream().map(this::toResponse).toList());
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

    private ProfessorResponse toResponse(Professor p) {
        return new ProfessorResponse(p.getId(), p.getNome(), p.getCpf(), p.getEmail(), p.getTelefone());
    }

    private ProfessorDisponibilidadeResponse toDisponibilidadeResponse(ProfessorDisponibilidade d) {
        return new ProfessorDisponibilidadeResponse(d.getId(), d.getProfessorId(),
                d.getDiaSemana().name(), d.getHoraInicio(), d.getHoraFim());
    }

    private TurmaDisciplinaProfessorResponse toTDPResponse(TurmaDisciplinaProfessor tdp) {
        return new TurmaDisciplinaProfessorResponse(tdp.getId(), tdp.getTurmaId(), tdp.getDisciplinaId(),
                tdp.getProfessorId(), tdp.getAnoLetivoId(), tdp.getDiaSemana().name(),
                tdp.getHoraInicio(), tdp.getHoraFim());
    }
}
