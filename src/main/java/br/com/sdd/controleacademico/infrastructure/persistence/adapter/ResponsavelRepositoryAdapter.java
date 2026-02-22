package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.ResponsavelRepositoryPort;
import br.com.sdd.controleacademico.domain.model.Responsavel;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.ResponsavelEntity;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.ResponsavelMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringResponsavelRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ResponsavelRepositoryAdapter implements ResponsavelRepositoryPort {

    private final SpringResponsavelRepository jpaRepository;
    private final ResponsavelMapper mapper;

    public ResponsavelRepositoryAdapter(SpringResponsavelRepository jpaRepository,
            ResponsavelMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Responsavel salvar(Responsavel responsavel) {
        ResponsavelEntity entity = mapper.toEntity(responsavel);
        ResponsavelEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Responsavel atualizar(Responsavel responsavel) {
        // Para update, precisamos garantir que o ID é mantido e a entidade é
        // atualizada.
        // O metodo save do Spring Data JPA serve para insert e update se o ID existir.
        // Como estamos mapeando do dominio para entidade, o ID deve estar presente.
        return salvar(responsavel);
    }

    @Override
    public Optional<Responsavel> buscarPorId(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Responsavel> buscarPorCpf(String cpf) {
        return jpaRepository.findByCpf(cpf)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existePorCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }

    @Override
    public void deletar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Responsavel> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public br.com.sdd.controleacademico.domain.model.PaginationResult<Responsavel> listarPaginado(int page, int size) {
        org.springframework.data.domain.Page<br.com.sdd.controleacademico.infrastructure.persistence.entity.ResponsavelEntity> result = jpaRepository
                .findAll(org.springframework.data.domain.PageRequest.of(page, size,
                        org.springframework.data.domain.Sort.by("nome")));

        return new br.com.sdd.controleacademico.domain.model.PaginationResult<>(
                result.getContent().stream().map(mapper::toDomain).toList(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.getSize(),
                result.getNumber());
    }
}
