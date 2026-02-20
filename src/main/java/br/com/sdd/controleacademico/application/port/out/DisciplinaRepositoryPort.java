package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.Disciplina;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisciplinaRepositoryPort {
    Disciplina salvar(Disciplina disciplina);

    Optional<Disciplina> buscarPorId(UUID id);

    boolean existePorCodigo(String codigo);

    Disciplina atualizar(Disciplina disciplina);

    void deletar(UUID id);

    List<Disciplina> listarTodos();

    void adicionarProfessor(UUID disciplinaId, UUID professorId);

    void removerProfessor(UUID disciplinaId, UUID professorId);

    List<UUID> listarProfessorIds(UUID disciplinaId);

    List<Disciplina> listarPorProfessor(UUID professorId);

    boolean existeRelacaoProfessor(UUID disciplinaId, UUID professorId);
}
