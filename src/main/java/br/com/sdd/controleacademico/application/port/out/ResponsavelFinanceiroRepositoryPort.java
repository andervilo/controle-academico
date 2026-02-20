package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ResponsavelFinanceiroRepositoryPort {
    ResponsavelFinanceiro salvar(ResponsavelFinanceiro responsavel);

    Optional<ResponsavelFinanceiro> buscarPorId(UUID id);

    Optional<ResponsavelFinanceiro> buscarPorCpf(String cpf);

    boolean existePorCpf(String cpf);

    ResponsavelFinanceiro atualizar(ResponsavelFinanceiro responsavel);

    void deletar(UUID id);

    List<ResponsavelFinanceiro> listarTodos();
}
