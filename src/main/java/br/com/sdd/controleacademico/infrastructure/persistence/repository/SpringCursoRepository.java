package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpringCursoRepository extends JpaRepository<CursoEntity, UUID> {
    boolean existsByCodigo(String codigo);

    @Modifying
    @Query(value = "INSERT INTO curso_disciplina (curso_id, disciplina_id) VALUES (CAST(:cursoId AS UUID), CAST(:disciplinaId AS UUID))", nativeQuery = true)
    void adicionarDisciplina(@Param("cursoId") UUID cursoId, @Param("disciplinaId") UUID disciplinaId);

    @Modifying
    @Query(value = "DELETE FROM curso_disciplina WHERE curso_id = CAST(:cursoId AS UUID) AND disciplina_id = CAST(:disciplinaId AS UUID)", nativeQuery = true)
    void removerDisciplina(@Param("cursoId") UUID cursoId, @Param("disciplinaId") UUID disciplinaId);

    @Query(value = "SELECT disciplina_id FROM curso_disciplina WHERE curso_id = CAST(:cursoId AS UUID)", nativeQuery = true)
    List<UUID> listarDisciplinaIds(@Param("cursoId") UUID cursoId);

    @Query(value = "SELECT COUNT(*) > 0 FROM curso_disciplina WHERE curso_id = CAST(:cursoId AS UUID) AND disciplina_id = CAST(:disciplinaId AS UUID)", nativeQuery = true)
    boolean existeRelacao(@Param("cursoId") UUID cursoId, @Param("disciplinaId") UUID disciplinaId);
}
