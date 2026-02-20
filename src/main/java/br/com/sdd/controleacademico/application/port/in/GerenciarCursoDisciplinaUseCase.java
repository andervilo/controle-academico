package br.com.sdd.controleacademico.application.port.in;

import java.util.UUID;

public interface GerenciarCursoDisciplinaUseCase {
    void adicionarDisciplina(UUID cursoId, UUID disciplinaId);

    void removerDisciplina(UUID cursoId, UUID disciplinaId);
}
