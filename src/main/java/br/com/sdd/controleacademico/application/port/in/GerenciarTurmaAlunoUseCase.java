package br.com.sdd.controleacademico.application.port.in;

import java.util.UUID;

public interface GerenciarTurmaAlunoUseCase {
    void adicionarAluno(UUID turmaId, UUID alunoId);

    void removerAluno(UUID turmaId, UUID alunoId);
}
