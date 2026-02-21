package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.Escola;
import br.com.sdd.controleacademico.domain.model.FuncionarioAdministrativo;
import br.com.sdd.controleacademico.domain.model.PrecificacaoCurso;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConfiguracaoRepositoryPort {
    // Escola
    Optional<Escola> buscarEscola();

    void salvarEscola(Escola escola);

    // Equipe
    List<FuncionarioAdministrativo> listarEquipe();

    Optional<FuncionarioAdministrativo> buscarFuncionario(UUID id);

    void salvarFuncionario(FuncionarioAdministrativo funcionario);

    void deletarFuncionario(UUID id);

    // Financeiro
    List<PrecificacaoCurso> listarPrecificacoesPorAno(UUID anoLetivoId);

    void salvarPrecificacao(PrecificacaoCurso precificacao);
}
