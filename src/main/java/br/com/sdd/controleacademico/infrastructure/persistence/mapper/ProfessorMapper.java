package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.Professor;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.ProfessorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    ProfessorEntity toEntity(Professor domain);

    Professor toDomain(ProfessorEntity entity);
}
