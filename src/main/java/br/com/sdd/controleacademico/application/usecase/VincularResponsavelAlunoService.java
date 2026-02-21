package br.com.sdd.controleacademico.application.usecase;

import br.com.sdd.controleacademico.application.port.in.VincularResponsavelAlunoUseCase;
import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.application.port.out.ResponsavelRepositoryPort;
import br.com.sdd.controleacademico.domain.exception.RegraDeNegocioException;
import br.com.sdd.controleacademico.domain.model.AlunoResponsavel;
import br.com.sdd.controleacademico.domain.model.ParentescoEnum;

import java.util.UUID;

public class VincularResponsavelAlunoService implements VincularResponsavelAlunoUseCase {

    private final AlunoRepositoryPort alunoRepository;
    private final ResponsavelRepositoryPort responsavelRepository;

    public VincularResponsavelAlunoService(AlunoRepositoryPort alunoRepository,
            ResponsavelRepositoryPort responsavelRepository) {
        this.alunoRepository = alunoRepository;
        this.responsavelRepository = responsavelRepository;
    }

    @Override
    public void vincular(UUID alunoId, UUID responsavelId, ParentescoEnum parentesco, boolean permiteBuscarEscola,
            boolean contatoEmergencia) {

        var aluno = alunoRepository.buscarPorId(alunoId)
                .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));

        var responsavel = responsavelRepository.buscarPorId(responsavelId)
                .orElseThrow(() -> new RegraDeNegocioException("Responsável não encontrado"));

        boolean jaVinculado = aluno.getResponsaveis().stream()
                .anyMatch(vinculo -> vinculo.getResponsavelId().equals(responsavelId));

        if (jaVinculado) {
            throw new RegraDeNegocioException("Este responsável já está vinculado a este aluno");
        }

        var novoVinculo = AlunoResponsavel.criar(alunoId, responsavelId, parentesco, permiteBuscarEscola,
                contatoEmergencia);

        alunoRepository.adicionarResponsavel(alunoId, novoVinculo);
    }
}
