package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.Responsavel;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.ResponsavelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponsavelMapper {
    ResponsavelEntity toEntity(Responsavel domain);

    Responsavel toDomain(ResponsavelEntity entity);
}
