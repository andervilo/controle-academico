package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.DisciplinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpringDisciplinaRepository extends JpaRepository<DisciplinaEntity, UUID> {
    boolean existsByCodigo(String codigo);

    @Modifying
    @Query(value = "INSERT INTO professor_disciplina (disciplina_id, professor_id) VALUES (CAST(:disciplinaId AS UUID), CAST(:professorId AS UUID))", nativeQuery = true)
    void adicionarProfessor(@Param("disciplinaId") UUID disciplinaId, @Param("professorId") UUID professorId);

    @Modifying
    @Query(value = "DELETE FROM professor_disciplina WHERE disciplina_id = CAST(:disciplinaId AS UUID) AND professor_id = CAST(:professorId AS UUID)", nativeQuery = true)
    void removerProfessor(@Param("disciplinaId") UUID disciplinaId, @Param("professorId") UUID professorId);

    @Query(value = "SELECT professor_id FROM professor_disciplina WHERE disciplina_id = CAST(:disciplinaId AS UUID)", nativeQuery = true)
    List<UUID> listarProfessorIds(@Param("disciplinaId") UUID disciplinaId);

    @Query(value = "SELECT d.* FROM disciplina d JOIN professor_disciplina pd ON d.id = pd.disciplina_id WHERE pd.professor_id = CAST(:professorId AS UUID) AND d.deleted = false", nativeQuery = true)
    List<DisciplinaEntity> listarPorProfessor(@Param("professorId") UUID professorId);

    @Query(value = "SELECT COUNT(*) > 0 FROM professor_disciplina WHERE disciplina_id = CAST(:disciplinaId AS UUID) AND professor_id = CAST(:professorId AS UUID)", nativeQuery = true)
    boolean existeRelacao(@Param("disciplinaId") UUID disciplinaId, @Param("professorId") UUID professorId);
}
