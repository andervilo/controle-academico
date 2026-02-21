package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.AlunoResponsavelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringAlunoResponsavelRepository extends JpaRepository<AlunoResponsavelEntity, UUID> {
}
