package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.Aluno;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.AlunoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlunoMapper {
    @Mapping(target = "responsavel", ignore = true)
    @Mapping(target = "responsaveis", ignore = true)
    AlunoEntity toEntity(Aluno domain);

    @Mapping(target = "responsaveis", ignore = true)
    Aluno toDomain(AlunoEntity entity);
}
