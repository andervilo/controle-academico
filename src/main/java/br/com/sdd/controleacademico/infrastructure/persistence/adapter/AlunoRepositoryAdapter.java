package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.AlunoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Aluno;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.AlunoEntity;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.AlunoMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringAlunoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AlunoRepositoryAdapter implements AlunoRepositoryPort {

    private final SpringAlunoRepository jpaRepository;
    private final AlunoMapper mapper;

    public AlunoRepositoryAdapter(SpringAlunoRepository jpaRepository, AlunoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Aluno salvar(Aluno aluno) {
        AlunoEntity entity = mapper.toEntity(aluno);
        AlunoEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Aluno> buscarPorId(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Aluno> buscarPorMatricula(String matricula) {
        return jpaRepository.findByMatricula(matricula)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existePorMatricula(String matricula) {
        return jpaRepository.existsByMatricula(matricula);
    }

    @Override
    public boolean existePorCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }

    @Override
    public Aluno atualizar(Aluno aluno) {
        return salvar(aluno);
    }

    @Override
    public void deletar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Aluno> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
