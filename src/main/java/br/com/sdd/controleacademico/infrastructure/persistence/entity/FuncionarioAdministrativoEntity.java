package br.com.sdd.controleacademico.infrastructure.persistence.entity;

import br.com.sdd.controleacademico.domain.model.CargoAdministrativo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "config_equipe")
@Getter
@Setter
@NoArgsConstructor
public class FuncionarioAdministrativoEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String cpf;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoAdministrativo cargo;

    private boolean ativo;
}
