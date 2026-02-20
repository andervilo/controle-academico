package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.Curso;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CursoRepositoryPort {
    Curso salvar(Curso curso);

    Optional<Curso> buscarPorId(UUID id);

    boolean existePorCodigo(String codigo);

    Curso atualizar(Curso curso);

    void deletar(UUID id);

    List<Curso> listarTodos();

    void adicionarDisciplina(UUID cursoId, UUID disciplinaId);

    void removerDisciplina(UUID cursoId, UUID disciplinaId);

    List<UUID> listarDisciplinaIds(UUID cursoId);

    boolean existeRelacaoDisciplina(UUID cursoId, UUID disciplinaId);
}
