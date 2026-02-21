package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Escola;
import br.com.sdd.controleacademico.domain.model.FuncionarioAdministrativo;
import br.com.sdd.controleacademico.domain.model.PrecificacaoCurso;

import java.util.List;
import java.util.UUID;

public interface GerenciarConfiguracaoUseCase {
    // Escola
    Escola buscarEscola();

    void atualizarEscola(Escola escola);

    // Equipe
    List<FuncionarioAdministrativo> listarEquipe();

    FuncionarioAdministrativo salvarFuncionario(FuncionarioAdministrativo funcionario);

    void deletarFuncionario(UUID id);

    // Financeiro
    List<PrecificacaoCurso> listarPrecificacoesPorAno(UUID anoLetivoId);

    void salvarPrecificacao(PrecificacaoCurso precificacao);
}
