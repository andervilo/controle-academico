package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.Turma;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.TurmaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TurmaMapper {
    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "anoLetivo", ignore = true)
    TurmaEntity toEntity(Turma domain);

    Turma toDomain(TurmaEntity entity);
}
