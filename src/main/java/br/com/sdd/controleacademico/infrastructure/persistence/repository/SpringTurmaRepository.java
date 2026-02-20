package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SpringTurmaRepository extends JpaRepository<TurmaEntity, UUID> {

    @Modifying
    @Query(value = "INSERT INTO turma_aluno (turma_id, aluno_id) VALUES (:turmaId, :alunoId)", nativeQuery = true)
    void adicionarAluno(UUID turmaId, UUID alunoId);

    @Modifying
    @Query(value = "DELETE FROM turma_aluno WHERE turma_id = :turmaId AND aluno_id = :alunoId", nativeQuery = true)
    void removerAluno(UUID turmaId, UUID alunoId);

    @Query(value = "SELECT aluno_id FROM turma_aluno WHERE turma_id = :turmaId", nativeQuery = true)
    List<UUID> listarAlunoIds(UUID turmaId);
}
