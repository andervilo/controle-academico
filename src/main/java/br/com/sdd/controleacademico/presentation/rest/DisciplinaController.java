package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.domain.model.Disciplina;
import br.com.sdd.controleacademico.presentation.rest.dto.DisciplinaRequest;
import br.com.sdd.controleacademico.presentation.rest.dto.DisciplinaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/disciplinas")
@Tag(name = "Disciplinas", description = "Gerenciamento de disciplinas")
public class DisciplinaController {

    private final CriarDisciplinaUseCase criarUseCase;
    private final BuscarDisciplinaUseCase buscarUseCase;
    private final AtualizarDisciplinaUseCase atualizarUseCase;
    private final DeletarDisciplinaUseCase deletarUseCase;
    private final ListarDisciplinasUseCase listarUseCase;
    private final GerenciarProfessorDisciplinaUseCase gerenciarProfessorUseCase;

    public DisciplinaController(CriarDisciplinaUseCase criarUseCase,
            BuscarDisciplinaUseCase buscarUseCase,
            AtualizarDisciplinaUseCase atualizarUseCase,
            DeletarDisciplinaUseCase deletarUseCase,
            ListarDisciplinasUseCase listarUseCase,
            GerenciarProfessorDisciplinaUseCase gerenciarProfessorUseCase) {
        this.criarUseCase = criarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.deletarUseCase = deletarUseCase;
        this.listarUseCase = listarUseCase;
        this.gerenciarProfessorUseCase = gerenciarProfessorUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar disciplina")
    public ResponseEntity<DisciplinaResponse> criar(@Valid @RequestBody DisciplinaRequest request) {
        Disciplina d = criarUseCase.criar(request.nome(), request.codigo(), request.cargaHoraria());
        return ResponseEntity.created(URI.create("/api/v1/disciplinas/" + d.getId())).body(toResponse(d));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar disciplina por ID")
    public ResponseEntity<DisciplinaResponse> buscarPorId(@PathVariable UUID id) {
        return buscarUseCase.buscarPorId(id).map(d -> ResponseEntity.ok(toResponse(d)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todas as disciplinas")
    public ResponseEntity<List<DisciplinaResponse>> listarTodas() {
        return ResponseEntity.ok(listarUseCase.listarTodas().stream().map(this::toResponse).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar disciplina")
    public ResponseEntity<DisciplinaResponse> atualizar(@PathVariable UUID id,
            @Valid @RequestBody DisciplinaRequest request) {
        return ResponseEntity.ok(toResponse(atualizarUseCase.atualizar(id, request.nome(), request.cargaHoraria())));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar disciplina")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{disciplinaId}/professores/{professorId}")
    @Operation(summary = "Adicionar professor à disciplina")
    public ResponseEntity<Void> adicionarProfessor(@PathVariable UUID disciplinaId, @PathVariable UUID professorId) {
        gerenciarProfessorUseCase.adicionarProfessor(disciplinaId, professorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{disciplinaId}/professores/{professorId}")
    @Operation(summary = "Remover professor da disciplina")
    public ResponseEntity<Void> removerProfessor(@PathVariable UUID disciplinaId, @PathVariable UUID professorId) {
        gerenciarProfessorUseCase.removerProfessor(disciplinaId, professorId);
        return ResponseEntity.noContent().build();
    }

    private DisciplinaResponse toResponse(Disciplina d) {
        return new DisciplinaResponse(d.getId(), d.getNome(), d.getCodigo(), d.getCargaHoraria());
    }
}
