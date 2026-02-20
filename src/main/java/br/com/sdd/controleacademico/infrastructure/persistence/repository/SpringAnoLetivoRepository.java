package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.AnoLetivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringAnoLetivoRepository extends JpaRepository<AnoLetivoEntity, UUID> {
    boolean existsByDescricao(String descricao);
}
