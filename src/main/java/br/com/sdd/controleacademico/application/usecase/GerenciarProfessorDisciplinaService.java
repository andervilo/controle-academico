package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.GerenciarProfessorDisciplinaUseCase;
import br.com.sdd.controleacademico.application.port.out.DisciplinaRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.ProfessorRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;

import java.util.UUID;

public class GerenciarProfessorDisciplinaService implements GerenciarProfessorDisciplinaUseCase {
    private final DisciplinaRepositoryPort disciplinaRepository;
    private final ProfessorRepositoryPort professorRepository;

    public GerenciarProfessorDisciplinaService(DisciplinaRepositoryPort disciplinaRepository,
            ProfessorRepositoryPort professorRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public void adicionarProfessor(UUID disciplinaId, UUID professorId) {
        disciplinaRepository.buscarPorId(disciplinaId)
                .orElseThrow(() -> new RegraDeNegocioException("Disciplina não encontrada"));
        professorRepository.buscarPorId(professorId)
                .orElseThrow(() -> new RegraDeNegocioException("Professor não encontrado"));
        disciplinaRepository.adicionarProfessor(disciplinaId, professorId);
    }

    @Override
    public void removerProfessor(UUID disciplinaId, UUID professorId) {
        disciplinaRepository.removerProfessor(disciplinaId, professorId);
    }
}
