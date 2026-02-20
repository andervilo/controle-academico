package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.Turma;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TurmaRepositoryPort {
    Turma salvar(Turma turma);

    Optional<Turma> buscarPorId(UUID id);

    Turma atualizar(Turma turma);

    void deletar(UUID id);

    List<Turma> listarTodos();

    void adicionarAluno(UUID turmaId, UUID alunoId);

    void removerAluno(UUID turmaId, UUID alunoId);

    List<UUID> listarAlunoIds(UUID turmaId);

    boolean existeRelacaoAluno(UUID turmaId, UUID alunoId);
}
