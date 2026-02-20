package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.application.port.in.BuscarResponsavelFinanceiroUseCase;
import br.com.sdd.controleacademico.application.port.in.CriarResponsavelFinanceiroUseCase;
import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;
import br.com.sdd.controleacademico.presentation.rest.dto.ResponsavelFinanceiroRequest;
import br.com.sdd.controleacademico.presentation.rest.dto.ResponsavelFinanceiroResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.presentation.rest.dto.AtualizarResponsavelFinanceiroRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/responsaveis-financeiros")
@Tag(name = "Responsável Financeiro", description = "Gerenciamento de Responsáveis Financeiros")
public class ResponsavelFinanceiroController {

        private final CriarResponsavelFinanceiroUseCase criarResponsavelUseCase;
        private final BuscarResponsavelFinanceiroUseCase buscarResponsavelUseCase;
        private final AtualizarResponsavelFinanceiroUseCase atualizarResponsavelUseCase;
        private final DeletarResponsavelFinanceiroUseCase deletarResponsavelUseCase;
        private final ListarResponsaveisFinanceirosUseCase listarResponsaveisUseCase;

        public ResponsavelFinanceiroController(CriarResponsavelFinanceiroUseCase criarResponsavelUseCase,
                        BuscarResponsavelFinanceiroUseCase buscarResponsavelUseCase,
                        AtualizarResponsavelFinanceiroUseCase atualizarResponsavelUseCase,
                        DeletarResponsavelFinanceiroUseCase deletarResponsavelUseCase,
                        ListarResponsaveisFinanceirosUseCase listarResponsaveisUseCase) {
                this.criarResponsavelUseCase = criarResponsavelUseCase;
                this.buscarResponsavelUseCase = buscarResponsavelUseCase;
                this.atualizarResponsavelUseCase = atualizarResponsavelUseCase;
                this.deletarResponsavelUseCase = deletarResponsavelUseCase;
                this.listarResponsaveisUseCase = listarResponsaveisUseCase;
        }

        @PostMapping
        @Operation(summary = "Criar novo Responsável", description = "Cria um registro de Responsável Financeiro")
        public ResponseEntity<ResponsavelFinanceiroResponse> criar(
                        @RequestBody @Valid ResponsavelFinanceiroRequest request) {
                var responsavel = criarResponsavelUseCase.criar(
                                request.nome(),
                                request.cpf(),
                                request.email(),
                                request.telefone());

                var response = new ResponsavelFinanceiroResponse(
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
        public ResponseEntity<ResponsavelFinanceiroResponse> buscarPorId(@PathVariable UUID id) {
                return buscarResponsavelUseCase.buscarPorId(id)
                                .map(this::toResponse)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        @Operation(summary = "Atualizar Responsável", description = "Atualiza dados de um Responsável Financeiro")
        public ResponseEntity<ResponsavelFinanceiroResponse> atualizar(@PathVariable UUID id,
                        @RequestBody @Valid AtualizarResponsavelFinanceiroRequest request) {
                var responsavel = atualizarResponsavelUseCase.atualizar(id, request.nome(), request.email(),
                                request.telefone());
                return ResponseEntity.ok(toResponse(responsavel));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Deletar Responsável", description = "Remove logicamente um Responsável Financeiro")
        public ResponseEntity<Void> deletar(@PathVariable UUID id) {
                deletarResponsavelUseCase.deletar(id);
                return ResponseEntity.noContent().build();
        }

        @GetMapping
        @Operation(summary = "Listar Responsáveis", description = "Lista todos os Responsáveis Financeiros")
        public ResponseEntity<List<ResponsavelFinanceiroResponse>> listar() {
                var lista = listarResponsaveisUseCase.listarTodos().stream()
                                .map(this::toResponse)
                                .toList();
                return ResponseEntity.ok(lista);
        }

        private ResponsavelFinanceiroResponse toResponse(ResponsavelFinanceiro responsavel) {
                return new ResponsavelFinanceiroResponse(
                                responsavel.getId(),
                                responsavel.getNome(),
                                responsavel.getCpf(),
                                responsavel.getEmail(),
                                responsavel.getTelefone());
        }
}
