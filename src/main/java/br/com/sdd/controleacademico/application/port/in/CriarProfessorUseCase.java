package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Professor;

public interface CriarProfessorUseCase {
    Professor criar(String nome, String cpf, String email, String telefone);
}
