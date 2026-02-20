package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.ResponsavelFinanceiroRepositoryPort;
import br.com.sdd.controleacademico.domain.model.ResponsavelFinanceiro;
import br.com.sdd.controleacademico.infrastructure.persistence.entity.ResponsavelFinanceiroEntity;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.ResponsavelFinanceiroMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringResponsavelFinanceiroRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ResponsavelFinanceiroRepositoryAdapter implements ResponsavelFinanceiroRepositoryPort {

    private final SpringResponsavelFinanceiroRepository jpaRepository;
    private final ResponsavelFinanceiroMapper mapper;

    public ResponsavelFinanceiroRepositoryAdapter(SpringResponsavelFinanceiroRepository jpaRepository,
            ResponsavelFinanceiroMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponsavelFinanceiro salvar(ResponsavelFinanceiro responsavel) {
        ResponsavelFinanceiroEntity entity = mapper.toEntity(responsavel);
        ResponsavelFinanceiroEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public ResponsavelFinanceiro atualizar(ResponsavelFinanceiro responsavel) {
        // Para update, precisamos garantir que o ID é mantido e a entidade é
        // atualizada.
        // O metodo save do Spring Data JPA serve para insert e update se o ID existir.
        // Como estamos mapeando do dominio para entidade, o ID deve estar presente.
        return salvar(responsavel);
    }

    @Override
    public Optional<ResponsavelFinanceiro> buscarPorId(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<ResponsavelFinanceiro> buscarPorCpf(String cpf) {
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
    public List<ResponsavelFinanceiro> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
