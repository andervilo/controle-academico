package br.com.sdd.controleacademico.infrastructure.persistence.repository;

import br.com.sdd.controleacademico.infrastructure.persistence.entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringAlunoRepository extends JpaRepository<AlunoEntity, UUID> {
    Optional<AlunoEntity> findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);

    boolean existsByCpf(String cpf);

    @org.springframework.data.jpa.repository.Query(value = "SELECT a.* FROM aluno a INNER JOIN turma_aluno ta ON a.id = ta.aluno_id WHERE ta.turma_id = :turmaId AND a.deleted = false", nativeQuery = true)
    java.util.List<AlunoEntity> findAlunosByTurmaId(
            @org.springframework.data.repository.query.Param("turmaId") UUID turmaId);

    @org.springframework.data.jpa.repository.Query(value = "SELECT a.* FROM aluno a WHERE a.deleted = false AND NOT EXISTS (SELECT 1 FROM turma_aluno ta WHERE ta.aluno_id = a.id AND ta.turma_id = :turmaId)", nativeQuery = true)
    java.util.List<AlunoEntity> findAlunosDisponiveisParaTurma(
            @org.springframework.data.repository.query.Param("turmaId") UUID turmaId);
}
