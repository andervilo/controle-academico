package br.com.sdd.controleacademico.application.port.in;

import java.util.List;
import java.util.UUID;

public interface GerenciarTurmaAlunoUseCase {
    void adicionarAluno(UUID turmaId, UUID alunoId);

    void adicionarAlunos(UUID turmaId, List<UUID> alunoIds);

    void removerAluno(UUID turmaId, UUID alunoId);
}
