package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.Aluno;
import java.util.Optional;
import java.util.UUID;

import java.util.List;

public interface AlunoRepositoryPort {
    Aluno salvar(Aluno aluno);

    Optional<Aluno> buscarPorId(UUID id);

    Optional<Aluno> buscarPorMatricula(String matricula);

    boolean existePorMatricula(String matricula);

    boolean existePorCpf(String cpf);

    Aluno atualizar(Aluno aluno);

    void deletar(UUID id);

    List<Aluno> listarTodos();
}
