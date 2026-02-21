package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.PrecificacaoCursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JpaPrecificacaoCursoRepository extends JpaRepository<PrecificacaoCursoEntity, UUID> {
    List<PrecificacaoCursoEntity> findByAnoLetivoId(UUID anoLetivoId);
}
