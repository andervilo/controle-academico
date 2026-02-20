package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.CriarAlunoUseCase;
import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.ResponsavelFinanceiroRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.Aluno;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.UUID;

public class CriarAlunoService implements CriarAlunoUseCase {

    private final AlunoRepositoryPort alunoRepository;
    private final ResponsavelFinanceiroRepositoryPort responsavelRepository;

    public CriarAlunoService(AlunoRepositoryPort alunoRepository,
            ResponsavelFinanceiroRepositoryPort responsavelRepository) {
        this.alunoRepository = alunoRepository;
        this.responsavelRepository = responsavelRepository;
    }

    @Override
    public Aluno criar(String nome, String cpf, String email, LocalDate dataNascimento, UUID responsavelFinanceiroId) {
        // Valida se Responsável existe
        var responsavel = responsavelRepository.buscarPorId(responsavelFinanceiroId)
                .orElseThrow(() -> new RegraDeNegocioException("Responsável Financeiro não encontrado"));

        // Valida duplicidade de CPF (se informado)
        if (cpf != null && !cpf.isBlank() && alunoRepository.existePorCpf(cpf)) {
            throw new RegraDeNegocioException("CPF de aluno já cadastrado");
        }

        // Gera Matrícula Única
        String matricula = gerarMatriculaUnica();

        var aluno = Aluno.criar(nome, matricula, cpf, email, dataNascimento, responsavel.getId());
        return alunoRepository.salvar(aluno);
    }

    private String gerarMatriculaUnica() {
        String matricula;
        do {
            matricula = Integer.toString(new SecureRandom().nextInt(90000) + 10000);
        } while (alunoRepository.existePorMatricula(matricula));
        return matricula;
    }
}
