package br.com.sdd.controleacademico.infrastructure.persistence.mapper;

import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.ResponsavelFinanceiroEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponsavelFinanceiroMapper {
    ResponsavelFinanceiroEntity toEntity(ResponsavelFinanceiro domain);

    ResponsavelFinanceiro toDomain(ResponsavelFinanceiroEntity entity);
}
