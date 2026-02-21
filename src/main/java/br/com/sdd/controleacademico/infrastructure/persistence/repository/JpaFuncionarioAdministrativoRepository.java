package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.FuncionarioAdministrativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaFuncionarioAdministrativoRepository extends JpaRepository<FuncionarioAdministrativoEntity, UUID> {
}
