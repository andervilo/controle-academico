package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.ListarProfessoresUseCase;
import br.com.sdd.controleacademico.application.port.out.ProfessorRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Professor;

import java.util.List;

public class ListarProfessoresService implements ListarProfessoresUseCase {
    private final ProfessorRepositoryPort repository;

    public ListarProfessoresService(ProfessorRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Professor> listarTodos() {
        return repository.listarTodos();
    }
}
