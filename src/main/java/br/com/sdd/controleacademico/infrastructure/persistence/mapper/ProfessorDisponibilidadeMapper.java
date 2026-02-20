package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.DiaSemana;
import br.com.sdd.controleacademico.domain.model.ProfessorDisponibilidade;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.ProfessorDisponibilidadeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProfessorDisponibilidadeMapper {

    @Mapping(target = "diaSemana", source = "diaSemana", qualifiedByName = "enumToString")
    ProfessorDisponibilidadeEntity toEntity(ProfessorDisponibilidade domain);

    @Mapping(target = "diaSemana", source = "diaSemana", qualifiedByName = "stringToEnum")
    ProfessorDisponibilidade toDomain(ProfessorDisponibilidadeEntity entity);

    @Named("enumToString")
    default String enumToString(DiaSemana diaSemana) {
        return diaSemana != null ? diaSemana.name() : null;
    }

    @Named("stringToEnum")
    default DiaSemana stringToEnum(String diaSemana) {
        return diaSemana != null ? DiaSemana.valueOf(diaSemana) : null;
    }
}
