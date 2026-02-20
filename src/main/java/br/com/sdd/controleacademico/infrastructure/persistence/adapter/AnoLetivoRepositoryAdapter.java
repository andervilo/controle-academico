package br.com.sdd.controleacademico.infrastructure.persistence.adapter;

import br.com.sdd.controleacademico.application.port.out.AnoLetivoRepositoryPort;
import br.com.sdd.controleacademico.domain.model.AnoLetivo;
import br.com.sdd.controleacademico.infrastructure.persistence.mapper.AnoLetivoMapper;
import br.com.sdd.controleacademico.infrastructure.persistence.repository.SpringAnoLetivoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AnoLetivoRepositoryAdapter implements AnoLetivoRepositoryPort {
    private final SpringAnoLetivoRepository jpaRepository;
    private final AnoLetivoMapper mapper;

    public AnoLetivoRepositoryAdapter(SpringAnoLetivoRepository jpaRepository, AnoLetivoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public AnoLetivo salvar(AnoLetivo anoLetivo) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(anoLetivo)));
    }

    @Override
    public Optional<AnoLetivo> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existePorDescricao(String descricao) {
        return jpaRepository.existsByDescricao(descricao);
    }

    @Override
    public AnoLetivo atualizar(AnoLetivo anoLetivo) {
        return salvar(anoLetivo);
    }

    @Override
    public void deletar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<AnoLetivo> listarTodos() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
