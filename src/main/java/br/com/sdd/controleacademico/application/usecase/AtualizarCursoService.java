package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.AtualizarCursoUseCase;
import br.com.sdd.controleacademico.application.port.out.CursoRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Curso;
import java.util.UUID;

public class AtualizarCursoService implements AtualizarCursoUseCase {
    private final CursoRepositoryPort repository;

    public AtualizarCursoService(CursoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Curso atualizar(UUID id, String nome, String descricao) {
        Curso curso = repository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Curso não encontrado"));
        curso.atualizar(nome, descricao);
        return repository.atualizar(curso);
    }
}
