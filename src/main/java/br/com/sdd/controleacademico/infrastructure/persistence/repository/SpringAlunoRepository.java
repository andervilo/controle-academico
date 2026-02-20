package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringAlunoRepository extends JpaRepository<AlunoEntity, UUID> {
    Optional<AlunoEntity> findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);

    boolean existsByCpf(String cpf);
}
