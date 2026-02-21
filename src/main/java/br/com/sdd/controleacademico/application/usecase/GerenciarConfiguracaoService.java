package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.GerenciarConfiguracaoUseCase;
import br.com.sdd.controleacademico.application.port.out.ConfiguracaoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Escola;
import br.com.sdd.controleacademico.domain.model.FuncionarioAdministrativo;
import br.com.sdd.controleacademico.domain.model.PrecificacaoCurso;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GerenciarConfiguracaoService implements GerenciarConfiguracaoUseCase {

    private final ConfiguracaoRepositoryPort repository;

    @Override
    public Escola buscarEscola() {
        return repository.buscarEscola()
                .orElseThrow(() -> new RuntimeException("Configuração da escola não encontrada"));
    }

    @Override
    @Transactional
    public void atualizarEscola(Escola escola) {
        repository.salvarEscola(escola);
    }

    @Override
    public List<FuncionarioAdministrativo> listarEquipe() {
        return repository.listarEquipe();
    }

    @Override
    @Transactional
    public FuncionarioAdministrativo salvarFuncionario(FuncionarioAdministrativo funcionario) {
        repository.salvarFuncionario(funcionario);
        return funcionario;
    }

    @Override
    @Transactional
    public void deletarFuncionario(UUID id) {
        repository.deletarFuncionario(id);
    }

    @Override
    public List<PrecificacaoCurso> listarPrecificacoesPorAno(UUID anoLetivoId) {
        return repository.listarPrecificacoesPorAno(anoLetivoId);
    }

    @Override
    @Transactional
    public void salvarPrecificacao(PrecificacaoCurso precificacao) {
        repository.salvarPrecificacao(precificacao);
    }
}
