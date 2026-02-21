package br.com.sdd.controleacademico.application.port.in;

import br.com.sdd.controleacademico.domain.model.Aluno;
import java.time.LocalDate;
import java.util.UUID;

public interface CriarAlunoUseCase {
    Aluno criar(String nome, String cpf, String email, String telefone, LocalDate dataNascimento, UUID responsavelId);
}
