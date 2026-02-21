package br.com.sdd.controleacademico.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioAdministrativo {
    private UUID id;
    private String nome;
    private String cpf;
    private String email;
    private CargoAdministrativo cargo;
    private boolean ativo;

    public static FuncionarioAdministrativo criar(String nome, String cpf, String email, CargoAdministrativo cargo) {
        return new FuncionarioAdministrativo(UUID.randomUUID(), nome, cpf, email, cargo, true);
    }
}
