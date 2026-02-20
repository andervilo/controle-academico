package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.GerenciarProfessorDisciplinaUseCase;
import br.com.sdd.controleacademico.application.port.out.DisciplinaRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.ProfessorRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;

import br.com.sdd.controleacademico.domain.model.Disciplina;

import java.util.List;
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

        if (disciplinaRepository.existeRelacaoProfessor(disciplinaId, professorId)) {
            throw new RegraDeNegocioException("Este professor já está habilitado nesta disciplina");
        }

        disciplinaRepository.adicionarProfessor(disciplinaId, professorId);
    }

    @Override
    public void removerProfessor(UUID disciplinaId, UUID professorId) {
        disciplinaRepository.removerProfessor(disciplinaId, professorId);
    }

    @Override
    public List<Disciplina> listarPorProfessor(UUID professorId) {
        return disciplinaRepository.listarPorProfessor(professorId);
    }
}
