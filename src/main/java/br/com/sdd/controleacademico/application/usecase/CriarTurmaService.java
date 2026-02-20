package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.CriarTurmaUseCase;
import br.com.sdd.controleacademico.application.port.out.AnoLetivoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.CursoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.TurmaRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Turma;

import java.util.UUID;

public class CriarTurmaService implements CriarTurmaUseCase {
    private final TurmaRepositoryPort turmaRepository;
    private final CursoRepositoryPort cursoRepository;
    private final AnoLetivoRepositoryPort anoLetivoRepository;

    public CriarTurmaService(TurmaRepositoryPort turmaRepository,
            CursoRepositoryPort cursoRepository,
            AnoLetivoRepositoryPort anoLetivoRepository) {
        this.turmaRepository = turmaRepository;
        this.cursoRepository = cursoRepository;
        this.anoLetivoRepository = anoLetivoRepository;
    }

    @Override
    public Turma criar(String nome, UUID cursoId, UUID anoLetivoId) {
        cursoRepository.buscarPorId(cursoId)
                .orElseThrow(() -> new RegraDeNegocioException("Curso não encontrado"));
        anoLetivoRepository.buscarPorId(anoLetivoId)
                .orElseThrow(() -> new RegraDeNegocioException("Ano Letivo não encontrado"));
        return turmaRepository.salvar(Turma.criar(nome, cursoId, anoLetivoId));
    }
}
