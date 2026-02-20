package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.AtualizarProfessorUseCase;
import br.com.sdd.controleacademico.application.port.out.ProfessorRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Professor;

import java.util.UUID;

public class AtualizarProfessorService implements AtualizarProfessorUseCase {
    private final ProfessorRepositoryPort repository;

    public AtualizarProfessorService(ProfessorRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Professor atualizar(UUID id, String nome, String email, String telefone) {
        Professor professor = repository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Professor não encontrado"));
        professor.atualizar(nome, email, telefone);
        return repository.atualizar(professor);
    }
}
