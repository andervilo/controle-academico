package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.GerenciarTurmaAlunoUseCase;
import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.TurmaRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;

import java.util.UUID;

public class GerenciarTurmaAlunoService implements GerenciarTurmaAlunoUseCase {
    private final TurmaRepositoryPort turmaRepository;
    private final AlunoRepositoryPort alunoRepository;

    public GerenciarTurmaAlunoService(TurmaRepositoryPort turmaRepository,
            AlunoRepositoryPort alunoRepository) {
        this.turmaRepository = turmaRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    public void adicionarAluno(UUID turmaId, UUID alunoId) {
        turmaRepository.buscarPorId(turmaId)
                .orElseThrow(() -> new RegraDeNegocioException("Turma não encontrada"));
        alunoRepository.buscarPorId(alunoId)
                .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));

        if (turmaRepository.existeRelacaoAluno(turmaId, alunoId)) {
            throw new RegraDeNegocioException("Este aluno já está matriculado nesta turma");
        }

        turmaRepository.adicionarAluno(turmaId, alunoId);
    }

    @Override
    public void removerAluno(UUID turmaId, UUID alunoId) {
        turmaRepository.removerAluno(turmaId, alunoId);
    }
}
