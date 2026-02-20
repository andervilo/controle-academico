package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.CriarDisciplinaUseCase;
import br.com.sdd.controleacademico.application.port.out.DisciplinaRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Disciplina;

public class CriarDisciplinaService implements CriarDisciplinaUseCase {
    private final DisciplinaRepositoryPort disciplinaRepository;

    public CriarDisciplinaService(DisciplinaRepositoryPort disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public Disciplina criar(String nome, String codigo, int cargaHoraria) {
        if (disciplinaRepository.existePorCodigo(codigo)) {
            throw new RegraDeNegocioException("Código de disciplina já cadastrado");
        }
        var disciplina = Disciplina.criar(nome, codigo, cargaHoraria);
        return disciplinaRepository.salvar(disciplina);
    }
}
