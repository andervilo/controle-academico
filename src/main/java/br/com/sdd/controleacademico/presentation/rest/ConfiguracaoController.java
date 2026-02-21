package br.com.sdd.controleacademico.presentation.rest;

import br.com.sdd.controleacademico.application.port.in.BuscarCursoUseCase;
import br.com.sdd.controleacademico.application.port.in.GerenciarConfiguracaoUseCase;
import br.com.sdd.controleacademico.domain.model.Escola;
import br.com.sdd.controleacademico.domain.model.FuncionarioAdministrativo;
import br.com.sdd.controleacademico.domain.model.PrecificacaoCurso;
import br.com.sdd.controleacademico.presentation.rest.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/config")
@Tag(name = "Configuração", description = "Gerenciamento de configurações da escola")
@RequiredArgsConstructor
public class ConfiguracaoController {

    private final GerenciarConfiguracaoUseCase gerenciarUseCase;
    private final BuscarCursoUseCase buscarCursoUseCase;

    // ── Escola ──────────────────────────────────────────────

    @GetMapping("/escola")
    @Operation(summary = "Buscar dados da escola")
    public ResponseEntity<EscolaResponse> buscarEscola() {
        Escola e = gerenciarUseCase.buscarEscola();
        return ResponseEntity.ok(toResponse(e));
    }

    @PutMapping("/escola")
    @Operation(summary = "Atualizar dados da escola")
    public ResponseEntity<Void> atualizarEscola(@RequestBody @Valid EscolaRequest request) {
        Escola e = gerenciarUseCase.buscarEscola();
        e.setNomeInstituicao(request.nomeInstituicao());
        e.setCnpj(request.cnpj());
        e.setInep(request.inep());
        e.setLogradouro(request.logradouro());
        e.setNumero(request.numero());
        e.setBairro(request.bairro());
        e.setCidade(request.cidade());
        e.setUf(request.uf());
        e.setCep(request.cep());
        e.setTelefone(request.telefone());
        e.setEmailContato(request.emailContato());
        e.setLogotipoBase64(request.logotipoBase64());

        gerenciarUseCase.atualizarEscola(e);
        return ResponseEntity.noContent().build();
    }

    // ── Equipe ──────────────────────────────────────────────

    @GetMapping("/equipe")
    @Operation(summary = "Listar equipe gestora")
    public ResponseEntity<List<FuncionarioAdministrativoResponse>> listarEquipe() {
        return ResponseEntity
                .ok(gerenciarUseCase.listarEquipe().stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @PostMapping("/equipe")
    @Operation(summary = "Salvar funcionário na equipe")
    public ResponseEntity<FuncionarioAdministrativoResponse> salvarFuncionario(
            @RequestBody @Valid FuncionarioAdministrativoRequest request) {
        FuncionarioAdministrativo f = FuncionarioAdministrativo.criar(request.nome(), request.cpf(), request.email(),
                request.cargo());
        f.setAtivo(request.ativo());
        return ResponseEntity.ok(toResponse(gerenciarUseCase.salvarFuncionario(f)));
    }

    @DeleteMapping("/equipe/{id}")
    @Operation(summary = "Remover funcionário da equipe")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable UUID id) {
        gerenciarUseCase.deletarFuncionario(id);
        return ResponseEntity.noContent().build();
    }

    // ── Financeiro ──────────────────────────────────────────

    @GetMapping("/financeiro")
    @Operation(summary = "Listar precificações por ano letivo")
    public ResponseEntity<List<PrecificacaoCursoResponse>> listarFinanceiro(@RequestParam UUID anoLetivoId) {
        return ResponseEntity.ok(gerenciarUseCase.listarPrecificacoesPorAno(anoLetivoId).stream().map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @PostMapping("/financeiro")
    @Operation(summary = "Salvar precificação de curso")
    public ResponseEntity<Void> salvarPrecificacao(@RequestBody @Valid PrecificacaoCursoRequest request) {
        // Tenta encontrar existente para atualizar ou cria nova
        List<PrecificacaoCurso> existentes = gerenciarUseCase.listarPrecificacoesPorAno(request.anoLetivoId());
        PrecificacaoCurso p = existentes.stream()
                .filter(item -> item.getCursoId().equals(request.cursoId()))
                .findFirst()
                .orElse(new PrecificacaoCurso());

        if (p.getId() == null)
            p.setId(UUID.randomUUID());
        p.setCursoId(request.cursoId());
        p.setAnoLetivoId(request.anoLetivoId());
        p.setValorMatricula(request.valorMatricula());
        p.setValorMensalidade(request.valorMensalidade());
        p.setQuantidadeMeses(request.quantidadeMeses());
        p.setDiaVencimentoPadrao(request.diaVencimentoPadrao());

        gerenciarUseCase.salvarPrecificacao(p);
        return ResponseEntity.noContent().build();
    }

    // Mappers
    private EscolaResponse toResponse(Escola e) {
        return new EscolaResponse(e.getId(), e.getNomeInstituicao(), e.getCnpj(), e.getInep(), e.getLogradouro(),
                e.getNumero(), e.getBairro(), e.getCidade(), e.getUf(), e.getCep(), e.getTelefone(),
                e.getEmailContato(), e.getLogotipoBase64());
    }

    private FuncionarioAdministrativoResponse toResponse(FuncionarioAdministrativo f) {
        return new FuncionarioAdministrativoResponse(f.getId(), f.getNome(), f.getCpf(), f.getEmail(), f.getCargo(),
                f.isAtivo());
    }

    private PrecificacaoCursoResponse toResponse(PrecificacaoCurso p) {
        String cursoNome = buscarCursoUseCase.buscarPorId(p.getCursoId()).map(c -> c.getNome()).orElse("N/A");
        return new PrecificacaoCursoResponse(p.getId(), p.getCursoId(), cursoNome, p.getAnoLetivoId(),
                p.getValorMatricula(), p.getValorMensalidade(), p.getQuantidadeMeses(), p.getDiaVencimentoPadrao());
    }
}
