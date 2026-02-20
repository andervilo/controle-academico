package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.AtualizarAlunoUseCase;
import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.ResponsavelFinanceiroRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Aluno;

import java.time.LocalDate;
import java.util.UUID;

public class AtualizarAlunoService implements AtualizarAlunoUseCase {

    private final AlunoRepositoryPort alunoRepository;
    private final ResponsavelFinanceiroRepositoryPort responsavelRepository;

    public AtualizarAlunoService(AlunoRepositoryPort alunoRepository,
            ResponsavelFinanceiroRepositoryPort responsavelRepository) {
        this.alunoRepository = alunoRepository;
        this.responsavelRepository = responsavelRepository;
    }

    @Override
    public Aluno atualizar(UUID id, String nome, String email, LocalDate dataNascimento, UUID responsavelFinanceiroId) {
        Aluno aluno = alunoRepository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));

        // Verifica se o responsável existe, se foi alterado
        if (!aluno.getResponsavelFinanceiroId().equals(responsavelFinanceiroId)) {
            responsavelRepository.buscarPorId(responsavelFinanceiroId)
                    .orElseThrow(() -> new RegraDeNegocioException("Responsável Financeiro não encontrado"));
        }

        aluno.atualizar(nome, email, dataNascimento, responsavelFinanceiroId);

        return alunoRepository.atualizar(aluno);
    }
}
