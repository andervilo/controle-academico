package br.com.sdd.controleacademico.infrastructure.config;

import br.com.sdd.controleacademico.application.port.in.*;
import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.ResponsavelFinanceiroRepositoryPort;
import br.com.sdd.controleacademico.application.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Configuração Spring que registra os Use Cases da camada application como
 * beans.
 * <p>
 * A camada application é pura Java (sem Spring). O wiring e o controle
 * transacional
 * ficam na camada infrastructure, respeitando as regras de dependência da Clean
 * Architecture.
 */
@Configuration
public class UseCaseConfig {

    // ─────────────────────────────────────────────────────────
    // Responsável Financeiro
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public CriarResponsavelFinanceiroUseCase criarResponsavelFinanceiroUseCase(
            ResponsavelFinanceiroRepositoryPort repository) {
        return new CriarResponsavelFinanceiroService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public BuscarResponsavelFinanceiroUseCase buscarResponsavelFinanceiroUseCase(
            ResponsavelFinanceiroRepositoryPort repository) {
        return new BuscarResponsavelFinanceiroService(repository);
    }

    @Bean
    @Transactional
    public AtualizarResponsavelFinanceiroUseCase atualizarResponsavelFinanceiroUseCase(
            ResponsavelFinanceiroRepositoryPort repository) {
        return new AtualizarResponsavelFinanceiroService(repository);
    }

    @Bean
    @Transactional
    public DeletarResponsavelFinanceiroUseCase deletarResponsavelFinanceiroUseCase(
            ResponsavelFinanceiroRepositoryPort repository) {
        return new DeletarResponsavelFinanceiroService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public ListarResponsaveisFinanceirosUseCase listarResponsaveisFinanceirosUseCase(
            ResponsavelFinanceiroRepositoryPort repository) {
        return new ListarResponsaveisFinanceirosService(repository);
    }

    // ─────────────────────────────────────────────────────────
    // Aluno
    // ─────────────────────────────────────────────────────────

    @Bean
    @Transactional
    public CriarAlunoUseCase criarAlunoUseCase(
            AlunoRepositoryPort alunoRepository,
            ResponsavelFinanceiroRepositoryPort responsavelRepository) {
        return new CriarAlunoService(alunoRepository, responsavelRepository);
    }

    @Bean
    @Transactional(readOnly = true)
    public BuscarAlunoUseCase buscarAlunoUseCase(AlunoRepositoryPort repository) {
        return new BuscarAlunoService(repository);
    }

    @Bean
    @Transactional
    public AtualizarAlunoUseCase atualizarAlunoUseCase(
            AlunoRepositoryPort alunoRepository,
            ResponsavelFinanceiroRepositoryPort responsavelRepository) {
        return new AtualizarAlunoService(alunoRepository, responsavelRepository);
    }

    @Bean
    @Transactional
    public DeletarAlunoUseCase deletarAlunoUseCase(AlunoRepositoryPort repository) {
        return new DeletarAlunoService(repository);
    }

    @Bean
    @Transactional(readOnly = true)
    public ListarAlunosUseCase listarAlunosUseCase(AlunoRepositoryPort repository) {
        return new ListarAlunosService(repository);
    }
}
