package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.ProfessorRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Professor;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.ProfessorMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringProfessorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProfessorRepositoryAdapter implements ProfessorRepositoryPort {
    private final SpringProfessorRepository jpaRepository;
    private final ProfessorMapper mapper;

    public ProfessorRepositoryAdapter(SpringProfessorRepository jpaRepository, ProfessorMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Professor salvar(Professor professor) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(professor)));
    }

    @Override
    public Optional<Professor> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existePorCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }

    @Override
    public Professor atualizar(Professor professor) {
        return salvar(professor);
    }

    @Override
    public void deletar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Professor> listarTodos() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
