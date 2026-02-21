package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Aluno;
import java.time.LocalDate;
import java.util.UUID;

public interface AtualizarAlunoUseCase {
    Aluno atualizar(UUID id, String nome, String email, String telefone, LocalDate dataNascimento, UUID responsavelId);
}
