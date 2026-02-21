package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.AtualizarAlunoUseCase;
import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.ResponsavelRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Aluno;

import java.time.LocalDate;
import java.util.UUID;

public class AtualizarAlunoService implements AtualizarAlunoUseCase {

    private final AlunoRepositoryPort alunoRepository;
    private final ResponsavelRepositoryPort responsavelRepository;

    public AtualizarAlunoService(AlunoRepositoryPort alunoRepository,
            ResponsavelRepositoryPort responsavelRepository) {
        this.alunoRepository = alunoRepository;
        this.responsavelRepository = responsavelRepository;
    }

    @Override
    public Aluno atualizar(UUID id, String nome, String email, String telefone, LocalDate dataNascimento,
            UUID responsavelId) {
        Aluno aluno = alunoRepository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));

        // Verifica se o responsável existe, se foi alterado
        if (!aluno.getResponsavelId().equals(responsavelId)) {
            responsavelRepository.buscarPorId(responsavelId)
                    .orElseThrow(() -> new RegraDeNegocioException("Responsável Financeiro não encontrado"));
        }

        aluno.atualizar(nome, email, telefone, dataNascimento, responsavelId);

        return alunoRepository.atualizar(aluno);
    }
}
