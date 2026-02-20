package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringProfessorRepository extends JpaRepository<ProfessorEntity, UUID> {
    boolean existsByCpf(String cpf);
}
