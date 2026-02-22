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
    private final br.com.sdd.controleacademico.infrastructure.persistence.mapper.ResponsavelMapper responsavelMapper;
    private final br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringAlunoResponsavelRepository alunoResponsavelRepository;

    public AlunoRepositoryAdapter(SpringAlunoRepository jpaRepository, AlunoMapper mapper,
            br.com.sdd.controleacademico.infrastructure.persistence.mapper.ResponsavelMapper responsavelMapper,
            br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringAlunoResponsavelRepository alunoResponsavelRepository) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.responsavelMapper = responsavelMapper;
        this.alunoResponsavelRepository = alunoResponsavelRepository;
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
    public void adicionarResponsavel(UUID alunoId, br.com.sdd.controleacademico.domain.model.AlunoResponsavel vinculo) {
        br.com.sdd.controleacademico.infrastructure.persistence.entity.AlunoResponsavelEntity entity = new br.com.sdd.controleacademico.infrastructure.persistence.entity.AlunoResponsavelEntity();
        entity.setId(vinculo.getId());
        entity.setAlunoId(vinculo.getAlunoId());
        entity.setResponsavelId(vinculo.getResponsavelId());
        entity.setParentesco(vinculo.getParentesco());
        entity.setPermiteBuscarEscola(vinculo.isPermiteBuscarEscola());
        entity.setContatoEmergencia(vinculo.isContatoEmergencia());
        entity.setCreatedAt(vinculo.getCreatedAt());
        entity.setUpdatedAt(vinculo.getUpdatedAt());

        alunoResponsavelRepository.save(entity);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<br.com.sdd.controleacademico.domain.model.AlunoResponsavelDetalhe> listarResponsaveisPorAluno(
            UUID alunoId) {
        var alunoEntityOptional = jpaRepository.findById(alunoId);
        if (alunoEntityOptional.isPresent()) {
            return alunoEntityOptional.get().getResponsaveis().stream()
                    .map(vinculoEntity -> new br.com.sdd.controleacademico.domain.model.AlunoResponsavelDetalhe(
                            responsavelMapper.toDomain(vinculoEntity.getResponsavel()),
                            vinculoEntity.getParentesco(),
                            vinculoEntity.isPermiteBuscarEscola(),
                            vinculoEntity.isContatoEmergencia()))
                    .toList();
        }
        return List.of();
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

    @Override
    public br.com.sdd.controleacademico.domain.model.PaginationResult<Aluno> listarPaginado(int page, int size) {
        org.springframework.data.domain.Page<AlunoEntity> result = jpaRepository
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
    public List<Aluno> listarPorTurma(UUID turmaId) {
        return jpaRepository.findAlunosByTurmaId(turmaId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Aluno> listarDisponiveisParaTurma(UUID turmaId) {
        return jpaRepository.findAlunosDisponiveisParaTurma(turmaId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
