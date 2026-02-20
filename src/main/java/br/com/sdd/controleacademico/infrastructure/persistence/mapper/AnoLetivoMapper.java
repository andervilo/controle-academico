package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.AnoLetivoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnoLetivoMapper {
    AnoLetivoEntity toEntity(AnoLetivo domain);

    AnoLetivo toDomain(AnoLetivoEntity entity);
}
