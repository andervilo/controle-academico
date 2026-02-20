package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.Aluno;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.AlunoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlunoMapper {
    @Mapping(target = "responsavelFinanceiro", ignore = true)
    AlunoEntity toEntity(Aluno domain);

    Aluno toDomain(AlunoEntity entity);
}
