package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.Professor;
import br.com.sdd.controleacademico.domain.model.PaginationResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessorRepositoryPort {
    Professor salvar(Professor professor);

    Optional<Professor> buscarPorId(UUID id);

    boolean existePorCpf(String cpf);

    Professor atualizar(Professor professor);

    void deletar(UUID id);

    List<Professor> listarTodos();

    PaginationResult<Professor> listarPaginado(int page, int size);
}
