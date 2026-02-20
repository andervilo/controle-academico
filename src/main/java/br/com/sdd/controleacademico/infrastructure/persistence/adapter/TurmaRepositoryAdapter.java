package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.TurmaRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Turma;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.TurmaMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringTurmaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TurmaRepositoryAdapter implements TurmaRepositoryPort {
    private final SpringTurmaRepository jpaRepository;
    private final TurmaMapper mapper;

    public TurmaRepositoryAdapter(SpringTurmaRepository jpaRepository, TurmaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Turma salvar(Turma turma) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(turma)));
    }

    @Override
    public Optional<Turma> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Turma atualizar(Turma turma) {
        return salvar(turma);
    }

    @Override
    public void deletar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Turma> listarTodos() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void adicionarAluno(UUID turmaId, UUID alunoId) {
        jpaRepository.adicionarAluno(turmaId, alunoId);
    }

    @Override
    public void removerAluno(UUID turmaId, UUID alunoId) {
        jpaRepository.removerAluno(turmaId, alunoId);
    }

    @Override
    public List<UUID> listarAlunoIds(UUID turmaId) {
        return jpaRepository.listarAlunoIds(turmaId);
    }
}
