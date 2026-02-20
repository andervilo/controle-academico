package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.AtualizarTurmaUseCase;
import br.com.sdd.controleacademico.application.port.out.AnoLetivoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.CursoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.TurmaRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Turma;
import java.util.UUID;

public class AtualizarTurmaService implements AtualizarTurmaUseCase {
    private final TurmaRepositoryPort turmaRepository;
    private final CursoRepositoryPort cursoRepository;
    private final AnoLetivoRepositoryPort anoLetivoRepository;

    public AtualizarTurmaService(TurmaRepositoryPort turmaRepository,
            CursoRepositoryPort cursoRepository,
            AnoLetivoRepositoryPort anoLetivoRepository) {
        this.turmaRepository = turmaRepository;
        this.cursoRepository = cursoRepository;
        this.anoLetivoRepository = anoLetivoRepository;
    }

    @Override
    public Turma atualizar(UUID id, String nome, UUID cursoId, UUID anoLetivoId) {
        Turma turma = turmaRepository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Turma não encontrada"));
        if (!turma.getCursoId().equals(cursoId)) {
            cursoRepository.buscarPorId(cursoId).orElseThrow(() -> new RegraDeNegocioException("Curso não encontrado"));
        }
        if (!turma.getAnoLetivoId().equals(anoLetivoId)) {
            anoLetivoRepository.buscarPorId(anoLetivoId)
                    .orElseThrow(() -> new RegraDeNegocioException("Ano Letivo não encontrado"));
        }
        turma.atualizar(nome, cursoId, anoLetivoId);
        return turmaRepository.atualizar(turma);
    }
}
