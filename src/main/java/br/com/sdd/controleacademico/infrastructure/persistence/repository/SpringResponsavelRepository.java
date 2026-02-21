package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.ResponsavelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringResponsavelRepository extends JpaRepository<ResponsavelEntity, UUID> {
    Optional<ResponsavelEntity> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
