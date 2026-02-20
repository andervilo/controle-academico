package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.DisciplinaRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Disciplina;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.DisciplinaMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringDisciplinaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DisciplinaRepositoryAdapter implements DisciplinaRepositoryPort {
    private final SpringDisciplinaRepository jpaRepository;
    private final DisciplinaMapper mapper;

    public DisciplinaRepositoryAdapter(SpringDisciplinaRepository jpaRepository, DisciplinaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Disciplina salvar(Disciplina disciplina) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(disciplina)));
    }

    @Override
    public Optional<Disciplina> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return jpaRepository.existsByCodigo(codigo);
    }

    @Override
    public Disciplina atualizar(Disciplina disciplina) {
        return salvar(disciplina);
    }

    @Override
    public void deletar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Disciplina> listarTodos() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void adicionarProfessor(UUID disciplinaId, UUID professorId) {
        jpaRepository.adicionarProfessor(disciplinaId, professorId);
    }

    @Override
    public void removerProfessor(UUID disciplinaId, UUID professorId) {
        jpaRepository.removerProfessor(disciplinaId, professorId);
    }

    @Override
    public List<UUID> listarProfessorIds(UUID disciplinaId) {
        return jpaRepository.listarProfessorIds(disciplinaId);
    }
}
