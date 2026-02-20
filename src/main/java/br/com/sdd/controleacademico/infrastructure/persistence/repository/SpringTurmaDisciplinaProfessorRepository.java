package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.TurmaDisciplinaProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringTurmaDisciplinaProfessorRepository extends JpaRepository<TurmaDisciplinaProfessorEntity, UUID> {
    List<TurmaDisciplinaProfessorEntity> findByTurmaId(UUID turmaId);

    List<TurmaDisciplinaProfessorEntity> findByProfessorId(UUID professorId);

    List<TurmaDisciplinaProfessorEntity> findByProfessorIdAndAnoLetivoId(UUID professorId, UUID anoLetivoId);

    List<TurmaDisciplinaProfessorEntity> findByAnoLetivoId(UUID anoLetivoId);
}
