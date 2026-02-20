package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.DisciplinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SpringDisciplinaRepository extends JpaRepository<DisciplinaEntity, UUID> {
    boolean existsByCodigo(String codigo);

    @Modifying
    @Query(value = "INSERT INTO professor_disciplina (disciplina_id, professor_id) VALUES (:disciplinaId, :professorId)", nativeQuery = true)
    void adicionarProfessor(UUID disciplinaId, UUID professorId);

    @Modifying
    @Query(value = "DELETE FROM professor_disciplina WHERE disciplina_id = :disciplinaId AND professor_id = :professorId", nativeQuery = true)
    void removerProfessor(UUID disciplinaId, UUID professorId);

    @Query(value = "SELECT professor_id FROM professor_disciplina WHERE disciplina_id = :disciplinaId", nativeQuery = true)
    List<UUID> listarProfessorIds(UUID disciplinaId);
}
