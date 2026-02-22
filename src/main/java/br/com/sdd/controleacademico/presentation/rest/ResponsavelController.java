package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.application.port.in.BuscarResponsavelUseCase;
import br.com.sdd.controleacademico.application.port.in.CriarResponsavelUseCase;
import br.com.sdd.controleacademico.domain.model.Responsavel;
import br.com.sdd.controleacademico.presentation.rest.dto.ResponsavelRequest;
import br.com.sdd.controleacademico.presentation.rest.dto.ResponsavelResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.presentation.rest.dto.AtualizarResponsavelRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/responsaveis")
@Tag(name = "Responsável", description = "Gerenciamento de Responsáveis")
public class ResponsavelController {

        private final CriarResponsavelUseCase criarResponsavelUseCase;
        private final BuscarResponsavelUseCase buscarResponsavelUseCase;
        private final AtualizarResponsavelUseCase atualizarResponsavelUseCase;
        private final DeletarResponsavelUseCase deletarResponsavelUseCase;
        private final ListarResponsaveisUseCase listarResponsaveisUseCase;

        public ResponsavelController(CriarResponsavelUseCase criarResponsavelUseCase,
                        BuscarResponsavelUseCase buscarResponsavelUseCase,
                        AtualizarResponsavelUseCase atualizarResponsavelUseCase,
                        DeletarResponsavelUseCase deletarResponsavelUseCase,
                        ListarResponsaveisUseCase listarResponsaveisUseCase) {
                this.criarResponsavelUseCase = criarResponsavelUseCase;
                this.buscarResponsavelUseCase = buscarResponsavelUseCase;
                this.atualizarResponsavelUseCase = atualizarResponsavelUseCase;
                this.deletarResponsavelUseCase = deletarResponsavelUseCase;
                this.listarResponsaveisUseCase = listarResponsaveisUseCase;
        }

        @PostMapping
        @Operation(summary = "Criar novo Responsável", description = "Cria um registro genérico de Responsável")
        public ResponseEntity<ResponsavelResponse> criar(
                        @RequestBody @Valid ResponsavelRequest request) {
                var responsavel = criarResponsavelUseCase.criar(
                                request.nome(),
                                request.cpf(),
                                request.email(),
                                request.telefone());

                var response = new ResponsavelResponse(
                                responsavel.getId(),
                                responsavel.getNome(),
                                responsavel.getCpf(),
                                responsavel.getEmail(),
                                responsavel.getTelefone());

                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(responsavel.getId())
                                .toUri();

                return ResponseEntity.created(location).body(response);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Buscar por ID", description = "Retorna um responsável pelo seu ID")
        public ResponseEntity<ResponsavelResponse> buscarPorId(@PathVariable UUID id) {
                return buscarResponsavelUseCase.buscarPorId(id)
                                .map(this::toResponse)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        @Operation(summary = "Atualizar Responsável", description = "Atualiza dados de um Responsável")
        public ResponseEntity<ResponsavelResponse> atualizar(@PathVariable UUID id,
                        @RequestBody @Valid AtualizarResponsavelRequest request) {
                var responsavel = atualizarResponsavelUseCase.atualizar(id, request.nome(), request.email(),
                                request.telefone());
                return ResponseEntity.ok(toResponse(responsavel));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Deletar Responsável", description = "Remove logicamente um Responsável")
        public ResponseEntity<Void> deletar(@PathVariable UUID id) {
                deletarResponsavelUseCase.deletar(id);
                return ResponseEntity.noContent().build();
        }

        @GetMapping
        @Operation(summary = "Listar Responsáveis (paginado)", description = "Lista todos os Responsáveis de forma paginada")
        public ResponseEntity<br.com.sdd.controleacademico.presentation.rest.dto.PaginatedResponse<ResponsavelResponse>> listar(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {

                var result = listarResponsaveisUseCase.listarPaginado(page, size);

                var lista = result.content().stream()
                                .map(this::toResponse)
                                .toList();

                return ResponseEntity.ok(new br.com.sdd.controleacademico.presentation.rest.dto.PaginatedResponse<>(
                                lista,
                                result.totalElements(),
                                result.totalPages(),
                                result.size(),
                                result.number()));
        }

        @GetMapping("/todos")
        @Operation(summary = "Listar todos os responsáveis (sem paginação)")
        public ResponseEntity<java.util.List<ResponsavelResponse>> listarTodosSemPaginacao() {
                return ResponseEntity
                                .ok(listarResponsaveisUseCase.listarTodos().stream().map(this::toResponse).toList());
        }

        private ResponsavelResponse toResponse(Responsavel responsavel) {
                return new ResponsavelResponse(
                                responsavel.getId(),
                                responsavel.getNome(),
                                responsavel.getCpf(),
                                responsavel.getEmail(),
                                responsavel.getTelefone());
        }
}
