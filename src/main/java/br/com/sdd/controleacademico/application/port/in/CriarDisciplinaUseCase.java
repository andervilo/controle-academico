package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Disciplina;

public interface CriarDisciplinaUseCase {
    Disciplina criar(String nome, String codigo, int cargaHoraria);
}
