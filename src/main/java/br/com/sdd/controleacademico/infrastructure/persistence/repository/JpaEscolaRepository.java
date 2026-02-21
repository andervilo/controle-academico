package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.EscolaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaEscolaRepository extends JpaRepository<EscolaEntity, UUID> {
}
