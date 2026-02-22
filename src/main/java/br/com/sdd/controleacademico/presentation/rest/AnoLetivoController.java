package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import br.com.sdd.controleacademico.presentation.rest.dto.AnoLetivoRequest;
import br.com.sdd.controleacademico.presentation.rest.dto.AnoLetivoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/anos-letivos")
@Tag(name = "Anos Letivos", description = "Gerenciamento de anos letivos")
public class AnoLetivoController {

    private final CriarAnoLetivoUseCase criarUseCase;
    private final BuscarAnoLetivoUseCase buscarUseCase;
    private final AtualizarAnoLetivoUseCase atualizarUseCase;
    private final DeletarAnoLetivoUseCase deletarUseCase;
    private final ListarAnosLetivosUseCase listarUseCase;

    public AnoLetivoController(CriarAnoLetivoUseCase criarUseCase,
            BuscarAnoLetivoUseCase buscarUseCase,
            AtualizarAnoLetivoUseCase atualizarUseCase,
            DeletarAnoLetivoUseCase deletarUseCase,
            ListarAnosLetivosUseCase listarUseCase) {
        this.criarUseCase = criarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.deletarUseCase = deletarUseCase;
        this.listarUseCase = listarUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar ano letivo")
    public ResponseEntity<AnoLetivoResponse> criar(@Valid @RequestBody AnoLetivoRequest request) {
        AnoLetivo a = criarUseCase.criar(request.ano(), request.descricao(), request.dataInicio(), request.dataFim());
        return ResponseEntity.created(URI.create("/api/v1/anos-letivos/" + a.getId())).body(toResponse(a));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ano letivo por ID")
    public ResponseEntity<AnoLetivoResponse> buscarPorId(@PathVariable UUID id) {
        return buscarUseCase.buscarPorId(id).map(a -> ResponseEntity.ok(toResponse(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todos os anos letivos (paginado)")
    public ResponseEntity<br.com.sdd.controleacademico.presentation.rest.dto.PaginatedResponse<AnoLetivoResponse>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var result = listarUseCase.listarPaginado(page, size);
        List<AnoLetivoResponse> content = result.content().stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(new br.com.sdd.controleacademico.presentation.rest.dto.PaginatedResponse<>(
                content,
                result.totalElements(),
                result.totalPages(),
                result.size(),
                result.number()));
    }

    @GetMapping("/todos")
    @Operation(summary = "Listar todos os anos letivos (sem paginação)")
    public ResponseEntity<List<AnoLetivoResponse>> listarTodosSemPaginacao() {
        return ResponseEntity.ok(listarUseCase.listarTodos().stream().map(this::toResponse).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ano letivo")
    public ResponseEntity<AnoLetivoResponse> atualizar(@PathVariable UUID id,
            @Valid @RequestBody AnoLetivoRequest request) {
        // Para update, vamos manter o mesmo campo ativo que já existe — precisamos de
        // um campo extra
        AnoLetivo existing = buscarUseCase.buscarPorId(id).orElseThrow();
        return ResponseEntity.ok(toResponse(atualizarUseCase.atualizar(id, request.ano(), request.descricao(),
                request.dataInicio(), request.dataFim(), existing.isAtivo())));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ano letivo")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private AnoLetivoResponse toResponse(AnoLetivo a) {
        return new AnoLetivoResponse(a.getId(), a.getAno(), a.getDescricao(), a.getDataInicio(), a.getDataFim(),
                a.isAtivo());
    }
}
