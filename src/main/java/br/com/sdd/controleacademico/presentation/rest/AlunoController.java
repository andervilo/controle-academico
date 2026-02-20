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

    public AlunoController(CriarAlunoUseCase criarAlunoUseCase,
            AtualizarAlunoUseCase atualizarAlunoUseCase,
            DeletarAlunoUseCase deletarAlunoUseCase,
            ListarAlunosUseCase listarAlunosUseCase,
            BuscarAlunoUseCase buscarAlunoUseCase) {
        this.criarAlunoUseCase = criarAlunoUseCase;
        this.atualizarAlunoUseCase = atualizarAlunoUseCase;
        this.deletarAlunoUseCase = deletarAlunoUseCase;
        this.listarAlunosUseCase = listarAlunosUseCase;
        this.buscarAlunoUseCase = buscarAlunoUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar novo Aluno", description = "Cria um registro de Aluno")
    public ResponseEntity<AlunoResponse> criar(@RequestBody @Valid AlunoRequest request) {
        var aluno = criarAlunoUseCase.criar(
                request.nome(),
                request.cpf(),
                request.email(),
                request.dataNascimento(),
                request.responsavelFinanceiroId());

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
                request.dataNascimento(),
                request.responsavelFinanceiroId());
        return ResponseEntity.ok(toResponse(aluno));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Aluno", description = "Remove logicamente um Aluno")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarAlunoUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar Alunos", description = "Lista todos os Alunos")
    public ResponseEntity<List<AlunoResponse>> listar() {
        var lista = listarAlunosUseCase.listarTodos().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    private AlunoResponse toResponse(Aluno aluno) {
        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getMatricula(),
                aluno.getCpf(),
                aluno.getEmail(),
                aluno.getDataNascimento(),
                aluno.getResponsavelFinanceiroId());
    }
}
