package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.Responsavel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ResponsavelRepositoryPort {
    Responsavel salvar(Responsavel responsavel);

    Optional<Responsavel> buscarPorId(UUID id);

    Optional<Responsavel> buscarPorCpf(String cpf);

    boolean existePorCpf(String cpf);

    Responsavel atualizar(Responsavel responsavel);

    void deletar(UUID id);

    List<Responsavel> listarTodos();

    br.com.sdd.controleacademico.domain.model.PaginationResult<Responsavel> listarPaginado(int page, int size);
}
