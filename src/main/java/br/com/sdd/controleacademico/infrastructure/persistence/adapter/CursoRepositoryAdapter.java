package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.CursoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Curso;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.CursoMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringCursoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CursoRepositoryAdapter implements CursoRepositoryPort {
    private final SpringCursoRepository jpaRepository;
    private final CursoMapper mapper;

    public CursoRepositoryAdapter(SpringCursoRepository jpaRepository, CursoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Curso salvar(Curso curso) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(curso)));
    }

    @Override
    public Optional<Curso> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return jpaRepository.existsByCodigo(codigo);
    }

    @Override
    public Curso atualizar(Curso curso) {
        return salvar(curso);
    }

    @Override
    public void deletar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Curso> listarTodos() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void adicionarDisciplina(UUID cursoId, UUID disciplinaId) {
        jpaRepository.adicionarDisciplina(cursoId, disciplinaId);
    }

    @Override
    public void removerDisciplina(UUID cursoId, UUID disciplinaId) {
        jpaRepository.removerDisciplina(cursoId, disciplinaId);
    }

    @Override
    public List<UUID> listarDisciplinaIds(UUID cursoId) {
        return jpaRepository.listarDisciplinaIds(cursoId);
    }
}
