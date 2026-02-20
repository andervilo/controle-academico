package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.DeletarAlunoUseCase;
import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;

import java.util.UUID;

public class DeletarAlunoService implements DeletarAlunoUseCase {

    private final AlunoRepositoryPort repository;

    public DeletarAlunoService(AlunoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void deletar(UUID id) {
        if (repository.buscarPorId(id).isEmpty()) {
            throw new RegraDeNegocioException("Aluno não encontrado");
        }
        repository.deletar(id);
    }
}
