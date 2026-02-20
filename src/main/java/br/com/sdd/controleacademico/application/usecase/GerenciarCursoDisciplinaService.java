package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.GerenciarCursoDisciplinaUseCase;
import br.com.sdd.controleacademico.application.port.out.CursoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.DisciplinaRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;

import java.util.UUID;

public class GerenciarCursoDisciplinaService implements GerenciarCursoDisciplinaUseCase {
    private final CursoRepositoryPort cursoRepository;
    private final DisciplinaRepositoryPort disciplinaRepository;

    public GerenciarCursoDisciplinaService(CursoRepositoryPort cursoRepository,
            DisciplinaRepositoryPort disciplinaRepository) {
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public void adicionarDisciplina(UUID cursoId, UUID disciplinaId) {
        cursoRepository.buscarPorId(cursoId)
                .orElseThrow(() -> new RegraDeNegocioException("Curso não encontrado"));
        disciplinaRepository.buscarPorId(disciplinaId)
                .orElseThrow(() -> new RegraDeNegocioException("Disciplina não encontrada"));
        cursoRepository.adicionarDisciplina(cursoId, disciplinaId);
    }

    @Override
    public void removerDisciplina(UUID cursoId, UUID disciplinaId) {
        cursoRepository.removerDisciplina(cursoId, disciplinaId);
    }
}
