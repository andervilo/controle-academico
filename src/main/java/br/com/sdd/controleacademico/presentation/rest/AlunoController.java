package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.application.port.in.CriarAlunoUseCase;
import br.com.sdd.controleacademico.domain.model.Aluno;
import br.com.sdd.controleacademico.presentation.rest.dto.AlunoRequest;
import br.com.sdd.controleacademico.presentation.rest.dto.AlunoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.presentation.rest.dto.AtualizarAlunoRequest;
import br.com.sdd.controleacademico.presentation.rest.dto.VincularResponsavelAlunoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alunos")
@Tag(name = "Aluno", description = "Gerenciamento de Alunos")
public class AlunoController {

        private final CriarAlunoUseCase criarAlunoUseCase;
        private final AtualizarAlunoUseCase atualizarAlunoUseCase;
        private final DeletarAlunoUseCase deletarAlunoUseCase;
        private final ListarAlunosUseCase listarAlunosUseCase;
        private final BuscarAlunoUseCase buscarAlunoUseCase;
        private final VincularResponsavelAlunoUseCase vincularResponsavelAlunoUseCase;
        private final ListarResponsaveisPorAlunoUseCase listarResponsaveisPorAlunoUseCase;

        public AlunoController(CriarAlunoUseCase criarAlunoUseCase,
                        AtualizarAlunoUseCase atualizarAlunoUseCase,
                        DeletarAlunoUseCase deletarAlunoUseCase,
                        ListarAlunosUseCase listarAlunosUseCase,
                        BuscarAlunoUseCase buscarAlunoUseCase,
                        VincularResponsavelAlunoUseCase vincularResponsavelAlunoUseCase,
                        ListarResponsaveisPorAlunoUseCase listarResponsaveisPorAlunoUseCase) {
                this.criarAlunoUseCase = criarAlunoUseCase;
                this.atualizarAlunoUseCase = atualizarAlunoUseCase;
                this.deletarAlunoUseCase = deletarAlunoUseCase;
                this.listarAlunosUseCase = listarAlunosUseCase;
                this.buscarAlunoUseCase = buscarAlunoUseCase;
                this.vincularResponsavelAlunoUseCase = vincularResponsavelAlunoUseCase;
                this.listarResponsaveisPorAlunoUseCase = listarResponsaveisPorAlunoUseCase;
        }

        @PostMapping
        @Operation(summary = "Criar novo Aluno", description = "Cria um registro de Aluno")
        public ResponseEntity<AlunoResponse> criar(@RequestBody @Valid AlunoRequest request) {
                var aluno = criarAlunoUseCase.criar(
                                request.nome(),
                                request.cpf(),
                                request.email(),
                                request.telefone(),
                                request.dataNascimento(),
                                request.responsavelId());

                var response = toResponse(aluno);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(aluno.getId())
                                .toUri();

                return ResponseEntity.created(location).body(response);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Buscar por ID", description = "Retorna um aluno pelo seu ID")
        public ResponseEntity<AlunoResponse> buscarPorId(@PathVariable UUID id) {
                return buscarAlunoUseCase.buscarPorId(id)
                                .map(this::toResponse)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        @Operation(summary = "Atualizar Aluno", description = "Atualiza dados de um Aluno")
        public ResponseEntity<AlunoResponse> atualizar(@PathVariable UUID id,
                        @RequestBody @Valid AtualizarAlunoRequest request) {
                var aluno = atualizarAlunoUseCase.atualizar(id,
                                request.nome(),
                                request.email(),
                                request.telefone(),
                                request.dataNascimento(),
                                request.responsavelId());
                return ResponseEntity.ok(toResponse(aluno));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Deletar Aluno", description = "Remove logicamente um Aluno")
        public ResponseEntity<Void> deletar(@PathVariable UUID id) {
                deletarAlunoUseCase.deletar(id);
                return ResponseEntity.noContent().build();
        }

        @PostMapping("/{id}/responsaveis")
        @Operation(summary = "Vincular Responsável ao Aluno", description = "Associa um guardião/responsável já cadastrado ao Aluno, especificando parentesco.")
        public ResponseEntity<Void> vincularResponsavel(
                        @PathVariable UUID id,
                        @RequestBody @Valid VincularResponsavelAlunoRequest request) {

                vincularResponsavelAlunoUseCase.vincular(
                                id,
                                request.responsavelId(),
                                request.parentesco(),
                                request.permiteBuscarEscola(),
                                request.contatoEmergencia());

                return ResponseEntity.ok().build();
        }

        @GetMapping("/{id}/responsaveis")
        @Operation(summary = "Listar Responsáveis de um Aluno", description = "Retorna a lista de responsáveis vinculados ao aluno com seus detalhes.")
        public ResponseEntity<List<br.com.sdd.controleacademico.presentation.rest.dto.AlunoResponsavelResponse>> listarResponsaveis(
                        @PathVariable UUID id) {
                var lista = listarResponsaveisPorAlunoUseCase.listar(id).stream()
                                .map(detalhe -> new br.com.sdd.controleacademico.presentation.rest.dto.AlunoResponsavelResponse(
                                                new br.com.sdd.controleacademico.presentation.rest.dto.ResponsavelResponse(
                                                                detalhe.responsavel().getId(),
                                                                detalhe.responsavel().getNome(),
                                                                detalhe.responsavel().getCpf(),
                                                                detalhe.responsavel().getEmail(),
                                                                detalhe.responsavel().getTelefone()),
                                                detalhe.parentesco(),
                                                detalhe.permiteBuscarEscola(),
                                                detalhe.contatoEmergencia()))
                                .toList();
                return ResponseEntity.ok(lista);
        }

        @GetMapping
        @Operation(summary = "Listar Alunos (paginado)", description = "Lista todos os Alunos de forma paginada")
        public ResponseEntity<br.com.sdd.controleacademico.presentation.rest.dto.PaginatedResponse<AlunoResponse>> listar(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {

                var result = listarAlunosUseCase.listarPaginado(page, size);

                List<AlunoResponse> content = result.content().stream()
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
        @Operation(summary = "Listar todos os alunos (sem paginação)")
        public ResponseEntity<List<AlunoResponse>> listarTodosSemPaginacao() {
                return ResponseEntity.ok(listarAlunosUseCase.listarTodos().stream().map(this::toResponse).toList());
        }

        private AlunoResponse toResponse(Aluno aluno) {
                return new AlunoResponse(
                                aluno.getId(),
                                aluno.getNome(),
                                aluno.getMatricula(),
                                aluno.getCpf(),
                                aluno.getEmail(),
                                aluno.getTelefone(),
                                aluno.getDataNascimento(),
                                aluno.getResponsavelId());
        }
}
