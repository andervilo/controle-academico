package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.ResponsavelFinanceiroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringResponsavelFinanceiroRepository extends JpaRepository<ResponsavelFinanceiroEntity, UUID> {
    Optional<ResponsavelFinanceiroEntity> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
