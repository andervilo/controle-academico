package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.AtualizarDisciplinaUseCase;
import br.com.sdd.controleacademico.application.port.out.DisciplinaRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Disciplina;
import java.util.UUID;

public class AtualizarDisciplinaService implements AtualizarDisciplinaUseCase {
    private final DisciplinaRepositoryPort disciplinaRepository;

    public AtualizarDisciplinaService(DisciplinaRepositoryPort disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public Disciplina atualizar(UUID id, String nome, int cargaHoraria) {
        Disciplina disciplina = disciplinaRepository.buscarPorId(id)
                .orElseThrow(() -> new RegraDeNegocioException("Disciplina não encontrada"));
        disciplina.atualizar(nome, cargaHoraria);
        return disciplinaRepository.atualizar(disciplina);
    }
}
