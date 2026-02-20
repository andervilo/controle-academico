package br.com.sdd.controleacademico.application.port.in;

import java.util.UUID;

public interface GerenciarProfessorDisciplinaUseCase {
    void adicionarProfessor(UUID disciplinaId, UUID professorId);

    void removerProfessor(UUID disciplinaId, UUID professorId);
}
