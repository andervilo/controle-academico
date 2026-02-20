package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.ProfessorDisponibilidadeRepositoryPort;
import br.com.sdd.controleacademico.domain.model.ProfessorDisponibilidade;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.ProfessorDisponibilidadeMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringProfessorDisponibilidadeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProfessorDisponibilidadeRepositoryAdapter implements ProfessorDisponibilidadeRepositoryPort {

    private final SpringProfessorDisponibilidadeRepository jpaRepository;
    private final ProfessorDisponibilidadeMapper mapper;

    public ProfessorDisponibilidadeRepositoryAdapter(SpringProfessorDisponibilidadeRepository jpaRepository,
            ProfessorDisponibilidadeMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ProfessorDisponibilidade salvar(ProfessorDisponibilidade disponibilidade) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(disponibilidade)));
    }

    @Override
    public void deletar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<ProfessorDisponibilidade> listarPorProfessor(UUID professorId) {
        return jpaRepository.findByProfessorId(professorId).stream().map(mapper::toDomain).toList();
    }
}
