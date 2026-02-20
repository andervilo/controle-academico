package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.TurmaDisciplinaProfessorRepositoryPort;
import br.com.sdd.controleacademico.domain.model.TurmaDisciplinaProfessor;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.TurmaDisciplinaProfessorMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringTurmaDisciplinaProfessorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TurmaDisciplinaProfessorRepositoryAdapter implements TurmaDisciplinaProfessorRepositoryPort {

    private final SpringTurmaDisciplinaProfessorRepository jpaRepository;
    private final TurmaDisciplinaProfessorMapper mapper;

    public TurmaDisciplinaProfessorRepositoryAdapter(SpringTurmaDisciplinaProfessorRepository jpaRepository,
            TurmaDisciplinaProfessorMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TurmaDisciplinaProfessor salvar(TurmaDisciplinaProfessor atribuicao) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(atribuicao)));
    }

    @Override
    public void deletar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<TurmaDisciplinaProfessor> listarPorTurma(UUID turmaId) {
        return jpaRepository.findByTurmaId(turmaId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<TurmaDisciplinaProfessor> listarPorProfessor(UUID professorId) {
        return jpaRepository.findByProfessorId(professorId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<TurmaDisciplinaProfessor> listarPorProfessorEAnoLetivo(UUID professorId, UUID anoLetivoId) {
        return jpaRepository.findByProfessorIdAndAnoLetivoId(professorId, anoLetivoId).stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public List<TurmaDisciplinaProfessor> listarPorAnoLetivo(UUID anoLetivoId) {
        return jpaRepository.findByAnoLetivoId(anoLetivoId).stream().map(mapper::toDomain).toList();
    }
}
