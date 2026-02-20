package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SpringCursoRepository extends JpaRepository<CursoEntity, UUID> {
    boolean existsByCodigo(String codigo);

    @Modifying
    @Query(value = "INSERT INTO curso_disciplina (curso_id, disciplina_id) VALUES (:cursoId, :disciplinaId)", nativeQuery = true)
    void adicionarDisciplina(UUID cursoId, UUID disciplinaId);

    @Modifying
    @Query(value = "DELETE FROM curso_disciplina WHERE curso_id = :cursoId AND disciplina_id = :disciplinaId", nativeQuery = true)
    void removerDisciplina(UUID cursoId, UUID disciplinaId);

    @Query(value = "SELECT disciplina_id FROM curso_disciplina WHERE curso_id = :cursoId", nativeQuery = true)
    List<UUID> listarDisciplinaIds(UUID cursoId);
}
