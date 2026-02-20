package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.Curso;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.CursoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CursoMapper {
    CursoEntity toEntity(Curso domain);

    Curso toDomain(CursoEntity entity);
}
