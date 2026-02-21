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
public class Escola {
    private UUID id;
    private String nomeInstituicao;
    private String cnpj;
    private String inep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String telefone;
    private String emailContato;
    private String logotipoBase64;
}
