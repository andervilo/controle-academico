package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.Disciplina;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.DisciplinaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisciplinaMapper {
    DisciplinaEntity toEntity(Disciplina domain);

    Disciplina toDomain(DisciplinaEntity entity);
}
