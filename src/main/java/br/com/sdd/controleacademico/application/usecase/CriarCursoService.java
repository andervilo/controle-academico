package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.CriarCursoUseCase;
import br.com.sdd.controleacademico.application.port.out.CursoRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Curso;

public class CriarCursoService implements CriarCursoUseCase {
    private final CursoRepositoryPort repository;

    public CriarCursoService(CursoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Curso criar(String nome, String codigo, String descricao) {
        if (repository.existePorCodigo(codigo))
            throw new RegraDeNegocioException("Código de curso já cadastrado");
        return repository.salvar(Curso.criar(nome, codigo, descricao));
    }
}
