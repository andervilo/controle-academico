package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Responsavel;

public interface CriarResponsavelUseCase {
    Responsavel criar(String nome, String cpf, String email, String telefone);
}
