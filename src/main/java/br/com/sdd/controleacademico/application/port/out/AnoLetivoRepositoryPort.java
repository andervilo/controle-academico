package br.com.sdd.controleacademico.application.port.out;

import br.com.sdd.controleacademico.domain.model.AnoLetivo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnoLetivoRepositoryPort {
    AnoLetivo salvar(AnoLetivo anoLetivo);

    Optional<AnoLetivo> buscarPorId(UUID id);

    boolean existePorDescricao(String descricao);

    AnoLetivo atualizar(AnoLetivo anoLetivo);

    void deletar(UUID id);

    List<AnoLetivo> listarTodos();
}
