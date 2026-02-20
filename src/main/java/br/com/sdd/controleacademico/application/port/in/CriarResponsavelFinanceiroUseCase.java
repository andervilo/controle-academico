package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;

public interface CriarResponsavelFinanceiroUseCase {
    ResponsavelFinanceiro criar(String nome, String cpf, String email, String telefone);
}
