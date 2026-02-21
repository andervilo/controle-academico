package br.com.sdd.controleacademico.application.port.in;

import java.util.List;
import java.util.UUID;

public interface GerenciarCursoDisciplinaUseCase {
    void adicionarDisciplina(UUID cursoId, UUID disciplinaId);

    void removerDisciplina(UUID cursoId, UUID disciplinaId);

    List<UUID> listarDisciplinaIds(UUID cursoId);
}
