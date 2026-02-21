package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.ConfiguracaoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Escola;
import br.com.sdd.controleacademico.domain.model.FuncionarioAdministrativo;
import br.com.sdd.controleacademico.domain.model.PrecificacaoCurso;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.EscolaEntity;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.FuncionarioAdministrativoEntity;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.PrecificacaoCursoEntity;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.JpaEscolaRepository;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.JpaFuncionarioAdministrativoRepository;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.JpaPrecificacaoCursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConfiguracaoRepositoryAdapter implements ConfiguracaoRepositoryPort {

    private final JpaEscolaRepository jpaEscolaRepository;
    private final JpaFuncionarioAdministrativoRepository jpaFuncionarioRepository;
    private final JpaPrecificacaoCursoRepository jpaPrecificacaoRepository;

    @Override
    public Optional<Escola> buscarEscola() {
        return jpaEscolaRepository.findAll().stream().findFirst().map(this::toDomain);
    }

    @Override
    public void salvarEscola(Escola escola) {
        EscolaEntity entity = toEntity(escola);
        entity.setUpdatedAt(LocalDateTime.now());
        jpaEscolaRepository.save(entity);
    }

    @Override
    public List<FuncionarioAdministrativo> listarEquipe() {
        return jpaFuncionarioRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<FuncionarioAdministrativo> buscarFuncionario(UUID id) {
        return jpaFuncionarioRepository.findById(id).map(this::toDomain);
    }

    @Override
    public void salvarFuncionario(FuncionarioAdministrativo funcionario) {
        jpaFuncionarioRepository.save(toEntity(funcionario));
    }

    @Override
    public void deletarFuncionario(UUID id) {
        jpaFuncionarioRepository.deleteById(id);
    }

    @Override
    public List<PrecificacaoCurso> listarPrecificacoesPorAno(UUID anoLetivoId) {
        return jpaPrecificacaoRepository.findByAnoLetivoId(anoLetivoId).stream().map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void salvarPrecificacao(PrecificacaoCurso precificacao) {
        jpaPrecificacaoRepository.save(toEntity(precificacao));
    }

    // Mappers
    private Escola toDomain(EscolaEntity e) {
        return new Escola(e.getId(), e.getNomeInstituicao(), e.getCnpj(), e.getInep(), e.getLogradouro(), e.getNumero(),
                e.getBairro(), e.getCidade(), e.getUf(), e.getCep(), e.getTelefone(), e.getEmailContato(),
                e.getLogotipoBase64());
    }

    private EscolaEntity toEntity(Escola d) {
        EscolaEntity e = new EscolaEntity();
        e.setId(d.getId());
        e.setNomeInstituicao(d.getNomeInstituicao());
        e.setCnpj(d.getCnpj());
        e.setInep(d.getInep());
        e.setLogradouro(d.getLogradouro());
        e.setNumero(d.getNumero());
        e.setBairro(d.getBairro());
        e.setCidade(d.getCidade());
        e.setUf(d.getUf());
        e.setCep(d.getCep());
        e.setTelefone(d.getTelefone());
        e.setEmailContato(d.getEmailContato());
        e.setLogotipoBase64(d.getLogotipoBase64());
        return e;
    }

    private FuncionarioAdministrativo toDomain(FuncionarioAdministrativoEntity e) {
        return new FuncionarioAdministrativo(e.getId(), e.getNome(), e.getCpf(), e.getEmail(), e.getCargo(),
                e.isAtivo());
    }

    private FuncionarioAdministrativoEntity toEntity(FuncionarioAdministrativo d) {
        FuncionarioAdministrativoEntity e = new FuncionarioAdministrativoEntity();
        e.setId(d.getId());
        e.setNome(d.getNome());
        e.setCpf(d.getCpf());
        e.setEmail(d.getEmail());
        e.setCargo(d.getCargo());
        e.setAtivo(d.isAtivo());
        return e;
    }

    private PrecificacaoCurso toDomain(PrecificacaoCursoEntity e) {
        return new PrecificacaoCurso(e.getId(), e.getCursoId(), e.getAnoLetivoId(), e.getValorMatricula(),
                e.getValorMensalidade(), e.getQuantidadeMeses(), e.getDiaVencimentoPadrao());
    }

    private PrecificacaoCursoEntity toEntity(PrecificacaoCurso d) {
        PrecificacaoCursoEntity e = new PrecificacaoCursoEntity();
        e.setId(d.getId());
        e.setCursoId(d.getCursoId());
        e.setAnoLetivoId(d.getAnoLetivoId());
        e.setValorMatricula(d.getValorMatricula());
        e.setValorMensalidade(d.getValorMensalidade());
        e.setQuantidadeMeses(d.getQuantidadeMeses());
        e.setDiaVencimentoPadrao(d.getDiaVencimentoPadrao());
        return e;
    }
}
