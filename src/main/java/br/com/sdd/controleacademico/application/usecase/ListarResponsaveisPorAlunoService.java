package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.ListarResponsaveisPorAlunoUseCase;
import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.AlunoResponsavelDetalhe;

import java.util.List;
import java.util.UUID;

public class ListarResponsaveisPorAlunoService implements ListarResponsaveisPorAlunoUseCase {

    private final AlunoRepositoryPort alunoRepositoryPort;

    public ListarResponsaveisPorAlunoService(AlunoRepositoryPort alunoRepositoryPort) {
        this.alunoRepositoryPort = alunoRepositoryPort;
    }

    @Override
    public List<AlunoResponsavelDetalhe> listar(UUID alunoId) {
        var aluno = alunoRepositoryPort.buscarPorId(alunoId)
                .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));

        return alunoRepositoryPort.listarResponsaveisPorAluno(alunoId);
    }
}
