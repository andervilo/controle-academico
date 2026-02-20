package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.DiaSemana;
import br.com.sdd.controleacademico.domain.model.TurmaDisciplinaProfessor;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.TurmaDisciplinaProfessorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TurmaDisciplinaProfessorMapper {

    @Mapping(target = "diaSemana", source = "diaSemana", qualifiedByName = "enumToString")
    TurmaDisciplinaProfessorEntity toEntity(TurmaDisciplinaProfessor domain);

    @Mapping(target = "diaSemana", source = "diaSemana", qualifiedByName = "stringToEnum")
    TurmaDisciplinaProfessor toDomain(TurmaDisciplinaProfessorEntity entity);

    @Named("enumToString")
    default String enumToString(DiaSemana diaSemana) {
        return diaSemana != null ? diaSemana.name() : null;
    }

    @Named("stringToEnum")
    default DiaSemana stringToEnum(String diaSemana) {
        return diaSemana != null ? DiaSemana.valueOf(diaSemana) : null;
    }
}
