package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.DisciplinaRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Disciplina;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.DisciplinaMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringDisciplinaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public br.com.sdd.controleacademico.domain.model.PaginationResult<Disciplina> listarPaginado(int page, int size) {
        org.springframework.data.domain.Page<br.com.sdd.controleacademico.infrastructure.persistence.entity.DisciplinaEntity> result = jpaRepository
                .findAll(org.springframework.data.domain.PageRequest.of(page, size,
                        org.springframework.data.domain.Sort.by("nome")));

        return new br.com.sdd.controleacademico.domain.model.PaginationResult<>(
                result.getContent().stream().map(mapper::toDomain).toList(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.getSize(),
                result.getNumber());
    }

    @Override
    @Transactional
    public void adicionarProfessor(UUID disciplinaId, UUID professorId) {
        jpaRepository.adicionarProfessor(disciplinaId, professorId);
    }

    @Override
    @Transactional
    public void removerProfessor(UUID disciplinaId, UUID professorId) {
        jpaRepository.removerProfessor(disciplinaId, professorId);
    }

    @Override
    public List<UUID> listarProfessorIds(UUID disciplinaId) {
        return jpaRepository.listarProfessorIds(disciplinaId);
    }

    @Override
    public List<Disciplina> listarPorProfessor(UUID professorId) {
        return jpaRepository.listarPorProfessor(professorId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existeRelacaoProfessor(UUID disciplinaId, UUID professorId) {
        return jpaRepository.existeRelacao(disciplinaId, professorId);
    }
}
