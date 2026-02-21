package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.domain.model.Curso;
import br.com.sdd.controleacademico.domain.model.Disciplina;
import br.com.sdd.controleacademico.presentation.rest.dto.CursoRequest;
import br.com.sdd.controleacademico.presentation.rest.dto.CursoResponse;
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
@RequestMapping("/api/v1/cursos")
@Tag(name = "Cursos", description = "Gerenciamento de cursos")
public class CursoController {

    private final CriarCursoUseCase criarUseCase;
    private final BuscarCursoUseCase buscarUseCase;
    private final AtualizarCursoUseCase atualizarUseCase;
    private final DeletarCursoUseCase deletarUseCase;
    private final ListarCursosUseCase listarUseCase;
    private final GerenciarCursoDisciplinaUseCase gerenciarDisciplinaUseCase;
    private final BuscarDisciplinaUseCase buscarDisciplinaUseCase;

    public CursoController(CriarCursoUseCase criarUseCase,
            BuscarCursoUseCase buscarUseCase,
            AtualizarCursoUseCase atualizarUseCase,
            DeletarCursoUseCase deletarUseCase,
            ListarCursosUseCase listarUseCase,
            GerenciarCursoDisciplinaUseCase gerenciarDisciplinaUseCase,
            BuscarDisciplinaUseCase buscarDisciplinaUseCase) {
        this.criarUseCase = criarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.deletarUseCase = deletarUseCase;
        this.listarUseCase = listarUseCase;
        this.gerenciarDisciplinaUseCase = gerenciarDisciplinaUseCase;
        this.buscarDisciplinaUseCase = buscarDisciplinaUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar curso")
    public ResponseEntity<CursoResponse> criar(@Valid @RequestBody CursoRequest request) {
        Curso c = criarUseCase.criar(request.nome(), request.codigo(), request.descricao());
        return ResponseEntity.created(URI.create("/api/v1/cursos/" + c.getId())).body(toResponse(c));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID")
    public ResponseEntity<CursoResponse> buscarPorId(@PathVariable UUID id) {
        return buscarUseCase.buscarPorId(id).map(c -> ResponseEntity.ok(toResponse(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todos os cursos")
    public ResponseEntity<List<CursoResponse>> listarTodos() {
        return ResponseEntity.ok(listarUseCase.listarTodos().stream().map(this::toResponse).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar curso")
    public ResponseEntity<CursoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody CursoRequest request) {
        return ResponseEntity.ok(toResponse(atualizarUseCase.atualizar(id, request.nome(), request.descricao())));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar curso")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cursoId}/disciplinas/{disciplinaId}")
    @Operation(summary = "Adicionar disciplina ao curso")
    public ResponseEntity<Void> adicionarDisciplina(@PathVariable UUID cursoId, @PathVariable UUID disciplinaId) {
        gerenciarDisciplinaUseCase.adicionarDisciplina(cursoId, disciplinaId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cursoId}/disciplinas/{disciplinaId}")
    @Operation(summary = "Remover disciplina do curso")
    public ResponseEntity<Void> removerDisciplina(@PathVariable UUID cursoId, @PathVariable UUID disciplinaId) {
        gerenciarDisciplinaUseCase.removerDisciplina(cursoId, disciplinaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cursoId}/disciplinas")
    @Operation(summary = "Listar disciplinas do curso")
    public ResponseEntity<List<DisciplinaResponse>> listarDisciplinas(@PathVariable UUID cursoId) {
        List<DisciplinaResponse> disciplinas = gerenciarDisciplinaUseCase.listarDisciplinaIds(cursoId).stream()
                .map(id -> buscarDisciplinaUseCase.buscarPorId(id).orElse(null))
                .filter(d -> d != null)
                .map(this::toDisciplinaResponse)
                .toList();
        return ResponseEntity.ok(disciplinas);
    }

    private CursoResponse toResponse(Curso c) {
        return new CursoResponse(c.getId(), c.getNome(), c.getCodigo(), c.getDescricao());
    }

    private DisciplinaResponse toDisciplinaResponse(Disciplina d) {
        return new DisciplinaResponse(d.getId(), d.getNome(), d.getCodigo(), d.getCargaHoraria());
    }
}
