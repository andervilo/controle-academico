package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Curso;

public interface CriarCursoUseCase {
    Curso criar(String nome, String codigo, String descricao);
}
