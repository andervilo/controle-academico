package br.com.sdd.controleacademico.application.port.in;

import java.util.UUID;
import br.com.sdd.controleacademico.domain.model.ParentescoEnum;

public interface VincularResponsavelAlunoUseCase {
    void vincular(UUID alunoId, UUID responsavelId, ParentescoEnum parentesco, boolean permiteBuscarEscola,
            boolean contatoEmergencia);
}
