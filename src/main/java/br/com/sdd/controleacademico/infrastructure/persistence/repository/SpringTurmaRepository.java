package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpringTurmaRepository extends JpaRepository<TurmaEntity, UUID> {

    @Modifying
    @Query(value = "INSERT INTO turma_aluno (turma_id, aluno_id) VALUES (CAST(:turmaId AS UUID), CAST(:alunoId AS UUID))", nativeQuery = true)
    void adicionarAluno(@Param("turmaId") UUID turmaId, @Param("alunoId") UUID alunoId);

    @Modifying
    @Query(value = "DELETE FROM turma_aluno WHERE turma_id = CAST(:turmaId AS UUID) AND aluno_id = CAST(:alunoId AS UUID)", nativeQuery = true)
    void removerAluno(@Param("turmaId") UUID turmaId, @Param("alunoId") UUID alunoId);

    @Query(value = "SELECT aluno_id FROM turma_aluno WHERE turma_id = CAST(:turmaId AS UUID)", nativeQuery = true)
    List<UUID> listarAlunoIds(@Param("turmaId") UUID turmaId);

    @Query(value = "SELECT COUNT(*) > 0 FROM turma_aluno WHERE turma_id = CAST(:turmaId AS UUID) AND aluno_id = CAST(:alunoId AS UUID)", nativeQuery = true)
    boolean existeRelacao(@Param("turmaId") UUID turmaId, @Param("alunoId") UUID alunoId);
}
