package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.CriarProfessorUseCase;
import br.com.sdd.controleacademico.application.port.out.ProfessorRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Professor;

public class CriarProfessorService implements CriarProfessorUseCase {

    private final ProfessorRepositoryPort repository;

    public CriarProfessorService(ProfessorRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Professor criar(String nome, String cpf, String email, String telefone) {
        if (repository.existePorCpf(cpf)) {
            throw new RegraDeNegocioException("CPF de professor já cadastrado");
        }
        var professor = Professor.criar(nome, cpf, email, telefone);
        return repository.salvar(professor);
    }
}
